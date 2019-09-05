package com.zzh.mvvm.ui.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.zzh.mvvm.base.BaseViewModel;
import com.zzh.mvvm.base.IBaseView;
import com.zzh.mvvm.bus.Messenger;
import com.zzh.mvvm.bus.binding.command.BindingAction;
import com.zzh.mvvm.ui.container.ContainerActivity;
import com.zzh.mvvm.ui.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator.
 *
 * @date: 2019/1/17
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHLiveApp
 * @since 1.0
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private LoadingDialog mLoadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接收的参数方法
        initParams();
        initViewDataBinding(savedInstanceState);
        registerUIChangeLiveDataCallBack();
        initData();
        initViewObservable();
        if (viewModel != null) {
            viewModel.registerRxBus();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            Messenger.getDefault().unregister(viewModel);
            getLifecycle().removeObserver(viewModel);
            viewModel.removeRxBus();
            viewModel = null;
            if (binding != null) {
                binding.unbind();
            }
        }
    }

    private void initViewDataBinding(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, setLayoutIds(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        getLifecycle().addObserver(viewModel);
        viewModel.injectLifecycleProvider(this);
    }

    public void showDialog(String msg) {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadingDialog(this);
            mLoadDialog.setCanceledOnTouchOutside(false);
        }
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            mLoadDialog.setTipMsg(msg);
        } else {
            mLoadDialog.show(msg);
        }
    }

    public void dismissDialog() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            mLoadDialog.dismiss();
        }
    }


    private void registerUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                showDialog(title);
            }
        });

        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, v -> dismissDialog());
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, params -> {
            Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startActivity(clz, bundle);
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(this, params -> {
            String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startContainerActivity(canonicalName, bundle);
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, v -> finish());
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, v -> onBackPressed());
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    protected VM initViewModel() {
        return null;
    }

    protected abstract int initVariableId();

    /**
     * 布局资源
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract int setLayoutIds(Bundle savedInstanceState);

}
