package com.example.test.activity_and_fragments.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R


class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)


        fun romanToInt(s: String): Int {
            fun remove(arr: MutableList<String>, num: Int) {
                for (i in 1..num) {
                    arr.removeAt(0)
                }
            }

            var i = 0
            val arr = mutableListOf<String>()
            for (a in s) {
                arr.add(a.toString())
            }
            while (arr.isNotEmpty()) {
                val size = arr.size
                when (arr[0]) {
                    "I" -> {
                        if (size >= 2) {
                            when (arr[1]) {
                                "I" -> {
                                    if (size >= 3) {
                                        if (arr[2] == "I") {
                                            i += 3
                                            remove(arr, 3)
                                        } else {
                                            i += 2
                                            remove(arr, 2)
                                        }
                                    } else {
                                        i += 2
                                        remove(arr, 2)
                                    }
                                }
                                "V" -> {
                                    i += 4
                                    remove(arr, 2)
                                }
                                "X" -> {
                                    i += 9
                                    remove(arr, 2)
                                }
                                else -> {
                                    i += 1
                                    remove(arr, 1)
                                }
                            }
                        } else {
                            i += 1
                            remove(arr, 1)
                        }
                    }
                    "X" -> {
                        if (size >= 2) {
                            when (arr[1]) {
                                "X" -> {
                                    if (size >= 3) {
                                        if (arr[2] == "X") {
                                            i += 30
                                            remove(arr, 3)
                                        } else {
                                            i += 20
                                            remove(arr, 2)
                                        }
                                    } else {
                                        i += 20
                                        remove(arr, 2)
                                    }
                                }
                                "L" -> {
                                    i += 40
                                    remove(arr, 2)
                                }
                                "C" -> {
                                    i += 90
                                    remove(arr, 2)
                                }
                                else -> {
                                    i += 10
                                    remove(arr, 1)
                                }
                            }
                        } else {
                            i += 10
                            remove(arr, 1)
                        }
                    }
                    "C" -> {
                        if (size >= 2) {
                            when (arr[1]) {
                                "C" -> {
                                    if (size >= 3) {
                                        if (arr[2] == "C") {
                                            i += 300
                                            remove(arr, 3)
                                        } else {
                                            i += 200
                                            remove(arr, 2)
                                        }
                                    } else {
                                        i += 200
                                        remove(arr, 2)
                                    }
                                }
                                "D" -> {
                                    i += 400
                                    remove(arr, 2)
                                }
                                "M" -> {
                                    i += 900
                                    remove(arr, 2)
                                }
                                else -> {
                                    i += 100
                                    remove(arr, 1)
                                }
                            }
                        } else {
                            i += 100
                            remove(arr, 1)
                        }
                    }
                    "M" -> {
                        if (size >= 2) {
                            when (arr[1]) {
                                "M" -> {
                                    if (size >= 3) {
                                        if (arr[2] == "M") {
                                            i += 3000
                                            remove(arr, 3)
                                        } else {
                                            i += 2000
                                            remove(arr, 2)
                                        }
                                    } else {
                                        i += 2000
                                        remove(arr, 2)
                                    }
                                }
                                else -> {
                                    i += 1000
                                    remove(arr, 1)
                                }
                            }
                        } else {
                            i += 1000
                            remove(arr, 1)
                        }
                    }
                    "V" -> {
                        i += 5
                        remove(arr, 1)
                    }
                    "L" -> {
                        i += 50
                        remove(arr, 1)
                    }
                    "D" -> {
                        i += 500
                        remove(arr, 1)
                    }
                    else -> {
                        remove(arr, 1)
                    }
                }
            }
            return i
        }


        /*} catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        } */
    }
}


