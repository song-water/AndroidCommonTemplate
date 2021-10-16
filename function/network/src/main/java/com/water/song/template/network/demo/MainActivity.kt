//package com.water.song.template.network.demo
//
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        testLibrarySuspendCall()
//    }
//
//    private fun testLibraryCall() {
//        Log.e("HelloWorld", "testLibraryCall. start " + Thread.currentThread())
//        LibraryCallImpl.getInstance()
//            .callTestApi(LibraryService::class.java)
//            .queryBaidu()
//            .awaitTimeout(GlobalScope, 1000)
//            .onFailure {
//                Log.e("HelloWorld", "testLibraryCall onFailure " + it)
//                Log.e("HelloWorld", "testLibraryCall onFailure " + Thread.currentThread())
//            }
//            .onSuccess {
//                Log.e("HelloWorld", "testLibraryCallY onSuccess " + it.value.string())
//                Log.e("HelloWorld", "testLibraryCallY onSuccess " + Thread.currentThread())
//            }
//            .onComplete {
//                Log.e("HelloWorld", "testLibraryCall onComplete " + Thread.currentThread())
//            }
//            .delayStart(1000)
//    }
//
//    private fun testLibrarySuspendCall() {
//        GlobalScope.launch {
//            LibraryCallImpl.getInstance()
//                .callTestApi(LibraryService::class.java)
//                .queryBaidu()
//                .suspendAwaitTimeout(1000)
//                .onFailure {
//                    Log.e("HelloWorld", "testLibrarySuspendCall onFailure " + it)
//                    Log.e("HelloWorld", "testLibrarySuspendCall onFailure " + Thread.currentThread())
//                }
//                .onSuccess {
//                    Log.e("HelloWorld", "testLibrarySuspendCall onSuccess " + it.value.string())
//                    Log.e("HelloWorld", "testLibrarySuspendCall onSuccess " + Thread.currentThread())
//                    HttpResult.Okay(emptyList<String>(), it.response)
//                }
//                .followDo {
//                    Log.e("HelloWorld", "testLibrarySuspendCall onComplete " + Thread.currentThread())
//                }
//        }
//    }
//
//}