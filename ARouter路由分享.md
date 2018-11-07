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

   #### 1，首先声明一个接口 但是必须继承 IProvider
   ```java
   
           public interface HelloService extends IProvider {
             void sayHello(String name);
           }
```
   #### 2，实现所要声明的interface 并且添加注解
  
   ```java
   
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
      
      //首先创建这个类的时候会先执行init 这个方法 我们可以做一些初始化工作，以及需要使用上下文 context 
   ```
   #### 3 ，获取我们想要获取的Class
   
   ```java
   
   class MainActivity{
    /**
    * 获取Class代码
   */
    public void getModuleClass(){
          HelloService navigation = (HelloService) ARouter.getInstance().build("/module/HelloService").navigation();
          navigation.sayHello("李桂云");
    }
    /**
    *  获取Fragment代码
    */
     public void getModuleFragment() {
            Fragment fragment = (Fragment) ARouter.getInstance().build("/module/fragment").navigation();
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.beginTransaction().add(R.id.framelayout, fragment).commit();
        }
    
   }
   /**
   * 创建Fragment
   * */
   @Route(path = "/module/fragment")
   public class HelloFragment extends Fragment {
   
       @Nullable
       @Override
       public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           return inflater.inflate(R.layout.activity_mouble_1, container, false);
       }
   }
   
   /**
   * 拦截器
   *  priority 是每个拦截器的级别 数字越小几杯越高
   *  可以在每个模块里面写入一个拦截器 当有需要跳转到此模块都会经过此拦截器
   * init 这个方法是当创建拦截器的时候 会调用这个初始化方法
   * 
   **/
   @Interceptor(priority = 10)
   public class TestInterceptor implements IInterceptor {
   
       @Override
       public void process(Postcard postcard, InterceptorCallback callback) {
           callback.onContinue(postcard);
       }
   
       @Override
       public void init(Context context) {
           Log.d("test", "位于module重的拦截器初始化成功");
       }
   }
   
                 
   ```        
   ## 五  ARoute实现原理
    
                1，ARoute有两个sdk compiler  一个是Aroute，compiler是通过获取注解生成响应的文件，比如类的注解 就会根据类名 模块 sdk 生成一个文件，当Aroute进行初始化的时候！把生成的文件 加载到
                
                hashMap里面 当需要获取响应的类 根据 Activity  Group  service IPrivoce 进行分类获取
                
                2，Aroute Sdk 第一步先把编译的文件加载到Root响应的HashMap数据结构里面，第二部集成响应的数据 在数据列表中寻找响应的Class 首先我们ARoute 通过 单列模式+build模式 构建一个Aroute工具类
                
                然后根据类型调用 navigation（）方法 ，源码太多 就不贴了 
              
   

       
