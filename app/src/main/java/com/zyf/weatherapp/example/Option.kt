package com.zyf.weatherapp.example

/**
 * Created by zyf on 2018/8/27.
 */
sealed class Option<out T> {

    class Some<out T>: Option<T>()

    object None: Option<Nothing>()
}

open class A {
    open fun f(){print("A")}
    fun a(){print("a")}
}

interface B {
    fun f(){print("B")}
    fun b(){print("b")}
}

class C : A(), B{
    override fun f(){
        super<A>.f()
        super<B>.f()
    }


}