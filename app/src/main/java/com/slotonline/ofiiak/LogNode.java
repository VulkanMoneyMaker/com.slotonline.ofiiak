package com.slotonline.ofiiak;

public interface LogNode {
   void println(int priority, String tag, String msg, Throwable tr);
}