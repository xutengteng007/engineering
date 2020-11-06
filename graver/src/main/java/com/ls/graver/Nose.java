package com.ls.graver;

import com.ls.graver.Interceptor.Tool;
import com.ls.graver.Interceptor.impl.TimeInterceptor;
import com.ls.graver.Interceptor.impl.TraceInterceptor;
import com.ls.graver.context.EnableContext;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * 刀 -入口类
 */
public class Nose {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is an agent.");

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }
        };

        // todo 动态拦截路径
        new AgentBuilder.Default()
                .ignore(nameStartsWith("net.bytebuddy.")
                        .or(nameStartsWith("org.slf4j."))
                        .or(nameStartsWith("org.groovy."))
                        .or(nameContains("javassist"))
                        .or(nameContains(".asm."))
                        .or(nameContains(".reflectasm."))
                        .or(nameStartsWith("sun.reflect"))
                        .or(ElementMatchers.isSynthetic()))
                .type(buildMatch())
                .transform(new Transformer()).installOn(inst);
    }

    /**
     * 类匹配规则
     *
     * @return
     */
    public static ElementMatcher<? super TypeDescription> buildMatch() {
        ElementMatcher.Junction judge = new AbstractJunction<NamedElement>() {
            @Override
            public boolean matches(NamedElement target) {
                return true;
            }
        };
        judge = judge.and(not(isInterface())).and(not(isStatic()));
        return new ProtectiveShieldMatcher(judge);
    }

    /**
     * 修改操作
     */
    private static class Transformer implements AgentBuilder.Transformer {
        @Override
        public DynamicType.Builder<?> transform(final DynamicType.Builder<?> builder,
                                                final TypeDescription typeDescription,
                                                final ClassLoader classLoader,
                                                final JavaModule module) {
//            EnableContext enableContext = new EnableContext();
            if (typeDescription.getDeclaredAnnotations().stream().anyMatch(annotationDescription ->
                    annotationDescription.getAnnotationType().getSimpleName().contains("Controller"))) {
                return turnBuilder(builder, typeDescription, classLoader, module, null);
            }
            return builder;
        }
    }

    /**
     * 修改字节码
     *
     * @param builder
     * @param typeDescription
     * @param classLoader
     * @param module
     * @return
     */
    private static DynamicType.Builder<?> turnBuilder(DynamicType.Builder<?> builder,
                                                      TypeDescription typeDescription,
                                                      ClassLoader classLoader,
                                                      JavaModule module,
                                                      EnableContext enableContext) {

//        if (!enableContext.isObjectExtended()) {
//            builder = builder.defineField("_$ls_tool", Object.class, 2 | 64)
//                    .implement(Tool.class)
//                    // 设置get set 方法
//                    .intercept(FieldAccessor.ofField("_$ls_tool"));
//
//            enableContext.extendObjectCompleted();
//        }

        builder = builder.method(ElementMatchers.<MethodDescription>any())
                .intercept(MethodDelegation
                        .to(TraceInterceptor.class));

        return builder;
    }
}
