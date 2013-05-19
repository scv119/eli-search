package com.eli.index;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/10/12
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.

    IField is an annotation for Lucene to Index, for more information please read @@IndexType

 */



@Retention(RetentionPolicy.RUNTIME)
public @interface IField {
    IndexType[] indexTypes() default {IndexType.None};
}
