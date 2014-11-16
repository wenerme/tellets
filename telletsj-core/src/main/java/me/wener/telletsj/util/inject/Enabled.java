package me.wener.telletsj.util.inject;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 在扫描包的时候,用于标记启用
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface Enabled
{
}
