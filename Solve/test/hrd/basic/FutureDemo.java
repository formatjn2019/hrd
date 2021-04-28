package hrd.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureDemo {

    public static void main(String[] args) {
        //创建 Callable 实现类的实例
        MyCallable myCallable = new MyCallable();
        //使用 FutureTask 类来包装 Callable 对象，该 FutureTask 对象封装了该 Callable 对象的 call() 方法的返回值
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        String res = null;
        try {
            //使用 FutureTask 对象作为 Thread 对象的 target 创建并启动新线程
            //没这句，下句代码获取不到结果，会一直等待执行结果
            new Thread(futureTask, "线程1").start();
            //调用 FutureTask 对象的 get() 方法来获得子线程执行结束后的返回值
            res = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }
}

/**
 * 创建 Callable 接口的实现类，并实现 call() 方法
 */
class MyCallable implements Callable<String> {

    /**
     * 该 call() 方法将作为线程执行体，并且有返回值
     */
    @Override
    public String call() throws Exception {
        return "success";
    }

}
