package com.delta.delta_os.bean

import android.content.Context

class Session {
    companion object {
        @JvmStatic  // <-- notice the @JvmStatic annotation
        var idCliente:Long = 0;
        @JvmStatic
        lateinit  // <-- notice the @JvmStatic annotation
        var context:Context;
    }
}