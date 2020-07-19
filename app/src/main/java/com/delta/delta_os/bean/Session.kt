package com.delta.delta_os.bean

import android.content.Context

class Session {
    companion object {
        @JvmStatic  // <-- notice the @JvmStatic annotation
        var idCliente:Long = 0;
        @JvmStatic
        lateinit  // <-- notice the @JvmStatic annotation
        var context:Context;
        @JvmStatic
        var idLocal:Int = 199;
        @JvmStatic
        var idLocalCadAparelho:Int = 199;
        @JvmStatic
        var idLocalAparelhos:Int = -1;
        @JvmStatic
        var uuidCliente:String = "var";
    }
}