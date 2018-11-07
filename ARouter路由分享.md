# ARouter路由分享
## 一 为什么使用路由跳转
  现在项目的模块化 和插件化 越来越多！但是模块与模块之间的引用也很频繁，过度的模块依赖会导致项目很累赘，不进行依赖就能模块和模块之间交互是迫切的需求，所以
  引用了路由这个概念 目前使用比较多的路由就是ARouter
 ## 二 路由的好处和弊端
  好处：使用模块和模块之间的类，或者是跳转 无论是传参 还是跳转，完全无压力，并且支持intent的所有传参类型
  弊端：侵入型比较强，需要路由的地方必须使用注解
 ## 三 模块直接的跳转
 ### 1，无参数跳转
        Uri uri = Uri.parse("/modulea/one");
        ARouter.getInstance().build(uri).navigation();
 ### 2，有参数跳转
         Uri uri = Uri.parse("/modulea/one");
         ARouter.getInstance().build(uri).withInt("flag", 20).navigation();
 ### 3,bundle参数跳转
          Uri uri = Uri.parse("/modulea/one");
          Bundle bundle = new Bundle();
          bundle.putString("name", "李桂云");
          ARouter.getInstance().build(uri).withBundle("bundle", bundle).navigation();
## 四 获取其他模块的Class
    ### 1，首先声明一个接口 但是必须继承 IProvider
           public interface HelloService extends IProvider {
             void sayHello(String name);
           }
    ### 2，实现所要声明的interface
       @Route(path = "/module/HelloService")
    public class HelloServiceImpl implements HelloService {
    private Context mContext;

    @Override
    public void sayHello(String name) {
        Toast.makeText(mContext, "Hello" + name, Toast.LENGTH_LONG).show();
    }

    @Override
    public void init(Context context) {
        mContext = context;
        Log.d("Service", HelloService.class.getName() + "has init");
    }
}
           

       
