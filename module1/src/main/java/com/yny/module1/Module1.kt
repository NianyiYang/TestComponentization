package com.yny.module1

import android.content.Context
import com.yny.router.ModuleInit
import com.alibaba.android.arouter.facade.annotation.Route
import com.yny.router.AppModules

@Route(path = AppModules.module1AppInit)
class Module1 : ModuleInit {
    override fun init(context: Context?) {
        // 初始化模块
    }
}