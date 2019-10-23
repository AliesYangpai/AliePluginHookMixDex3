package org.alie.aliepluginhookmixdex3.plugin.activity;

import android.app.Activity;

/**
 * Created by Alie on 2019/10/23.
 * 类描述  启动模式
 * 版本
 */
public class ActivityMode extends Activity {

    /**
     * =====================1.定义所有启动模式=========================
     */

    /**
     * 标准的启动模式
     */
    private static class StanderStub extends ActivityMode {
    }

    /**
     * SingleTop模式
     */
    private static class SingleTopStub extends ActivityMode {
    }

    /**
     * SingleTask模式
     */
    private static class SingleTaskStub extends ActivityMode {
    }

    /**
     * singleInstance模式也是单例的，但和singleTask不同，singleTask只是任务栈内单例，
     * 系统里是可以有多个singleTask Activity实例的，而singleInstance Activity在整个系统里只有一个实例，
     * 启动一singleInstanceActivity时，系统会创建一个新的任务栈，并且这个任务栈只有他一个Activity。
     */
    private static class SingleInstanceStub extends ActivityMode {
    }



    /**
     * =====================2.定义支持的插件的数量=========================
     * 这里我们定义同一时刻支持5个插件，如果来了第6个，将最开始未使用的那个kill掉，
     */

    public static  class P01{
        public static class Standard00 extends StanderStub {
        }
        public static class SingleInstance00 extends SingleInstanceStub {
        }
        public static class SingleTask00 extends SingleTaskStub {
        }

        public static class SingleTop00 extends SingleTopStub {
        }
    }
    public static  class P02{
        public static class Standard00 extends StanderStub {
        }
        public static class SingleInstance00 extends SingleInstanceStub {
        }
        public static class SingleTask00 extends SingleTaskStub {
        }

        public static class SingleTop00 extends SingleTopStub {
        }
    }
    public static  class P03{
        public static class Standard00 extends StanderStub {
        }
        public static class SingleInstance00 extends SingleInstanceStub {
        }
        public static class SingleTask00 extends SingleTaskStub {
        }

        public static class SingleTop00 extends SingleTopStub {
        }
    }
    public static  class P04{
        public static class Standard00 extends StanderStub {
        }
        public static class SingleInstance00 extends SingleInstanceStub {
        }
        public static class SingleTask00 extends SingleTaskStub {
        }

        public static class SingleTop00 extends SingleTopStub {
        }
    }
    public static  class P05{
        public static class Standard00 extends StanderStub {
        }
        public static class SingleInstance00 extends SingleInstanceStub {
        }
        public static class SingleTask00 extends SingleTaskStub {
        }

        public static class SingleTop00 extends SingleTopStub {
        }
    }
}
