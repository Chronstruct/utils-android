package com.chronstruct.utils

/*
from https://gist.github.com/florina-muntenescu/697e543652b03d3d2a06703f5d6b44b5#file-datadatabase-kt-L40
 */

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(f: () -> Unit) {
  IO_EXECUTOR.execute(f)
}