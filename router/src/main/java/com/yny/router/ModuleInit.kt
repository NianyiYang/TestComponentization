package com.yny.router

import android.app.Application
import com.alibaba.android.arouter.facade.template.IProvider

interface ModuleInit : IProvider {
    fun initModule(application: Application)
}