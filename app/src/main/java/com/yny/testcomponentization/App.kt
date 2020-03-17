package com.yny.testcomponentization

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.yny.router.AppModules
import com.yny.router.ModuleInit

/**
 * 重写Application
 *
 * @author nianyi.yang
 * @date 2020-03-17 18:59
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initRouter()
        initModules()
    }

    /**
     * 初始化路由
     */
    private fun initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    /**
     * 遍历模块列表，如果能获取到，则初始化模块
     */
    private fun initModules() {
        val modules = getString(R.string.modules).split(",")
        modules.forEach { module ->
            val modulePath = AppModules.getModulePath(module)
            if (modulePath != null) {
                val navigation = ARouter.getInstance().build(modulePath).navigation()
                if (navigation != null && navigation is ModuleInit) {
                    navigation.init(this)
                }
            }
        }
    }
}