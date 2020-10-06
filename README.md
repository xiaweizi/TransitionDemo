## 前言

先直接上效果图：

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/af74da88de0549e9bd0fb57ef9b14205~tplv-k3u1fbpfcp-zoom-1.image)

相信大家在平常开发也会遇到类似的转场动画，如果想要要实现上图的效果有哪些方式呢？

首先分析一下转场过程，我们把起始 `View` 分别定义为 `startView` 和 `endView`。`startView` 为常见的列表布局，左侧头像和右侧为文本介绍；`endView` 为详情页面，置顶的大图和详细的文本介绍。

不难发现，这些元素都是对应关系，只不过起始状态的基本属性不同：

- **头像**，位置和大小以及 `scaleType` 发生变化
- **背景**，颜色、位置和大小发生变化
- **名称**，字体大小、颜色和位置发生变化
- **描述**，字体大小和位置发生变化

对于此效果，有很多办法可以实现，综合其实现成本和预期效果进行最终选择，我能想到的大概有三种：

1. 直接把上述的每个对象看做是独立个体，各自创建独立的动画对象，控制其执行和结束状态。

   这种方式，无疑是最简单粗暴的，但是实现和维护起来都很困难，更不容易拓展

2. 使用 [MotionLayout](https://developer.android.google.cn/training/constraint-layout/motion-layout-examples#drawerlayout_12)，不得不说很强大，是 Google 推崇的动画组件，基本不用编写 `java` 代码就可完成负责的手势和动画，后面有时间会介绍。

3. 使用 `Transition`，Google 在 Android 5.0 完整引入，虽没有 [MotionLayout](https://developer.android.google.cn/training/constraint-layout/motion-layout-examples#drawerlayout_12) 那么强大，但是其复用性很强，并且很容易理解，上手也很快。

今天咱们就以下面三个方向并结合对应效果来带大家了解一下 Transition。

1. 原生提供的 `Transition` 类
2. 自己实现 `Transition` 类
3. Scene

## 原生 Transition

### 准备

核心关键类 `TransitionManager`, `TransitionManager.beginDelayedTransition(ViewGroup viewGroup, Transition transition);` 作为动画的开始，传入需要做转场动画的父布局或根布局，随后改变 `View` 的相关属性，比如 `setVisible()`，便可自动完成转场动画效果。

默认实现的 `AutoTransition`，内部集成了基础动画：

```java
private void init() {
    setOrdering(ORDERING_SEQUENTIAL);
    addTransition(new Fade(Fade.OUT)).
            addTransition(new ChangeBounds()).
            addTransition(new Fade(Fade.IN));
}
```

### Slide、Fade 和 Explode

这三者作为 `Visibility` 的三个子类，通过控制 `view.setVisible()` 的方式来达到具体的效果。

**Fade**，淡出 出场，淡入 入场

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/fedb5ec3f373401fa1897938c4ea2c5b~tplv-k3u1fbpfcp-zoom-1.image)

**Slide**，向下离开屏幕出场，向上进入屏幕入场

![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/356081375df841399c5e09699cf906eb~tplv-k3u1fbpfcp-zoom-1.image)

**Explode**，四边散开出场，四边汇入入场

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/a85007fba17042f18600ef65cda98341~tplv-k3u1fbpfcp-zoom-1.image)

同样，可以通过：

```java
Fade fade = new Fade();
Slide slide = new Slide();
TransitionSet set = new TransitionSet();
set.addTransition(fade).addTransition(slide).setOrdering(TransitionSet.ORDERING_TOGETHER);
```
达到组合的效果：

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/20657740010043caa63cf037c35a1875~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeBounds

此处开始同一个页面场景的切换，`ChangeBounds` 当 View 的位置或者大小发生变化时触发对应的转场效果。比如：

```java
ChangeBounds transition = new ChangeBounds();
transition.setInterpolator(new AnticipateInterpolator());
TransitionManager.beginDelayedTransition(mRoot, transition);
ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view3.getLayoutParams();
if (layoutParams.leftMargin == 400) {
    layoutParams.leftMargin = 50;
} else {
    layoutParams.leftMargin = 400;
}
view3.setLayoutParams(layoutParams);
```

最终的效果：

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/8941bcdf9f2f4cb0a13fe0caa43c80a2~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeClipBounds

当调用 `view.setClipBounds()` 时会触发转场效果：

```java
ChangeClipBounds transition = new ChangeClipBounds();
transition.setInterpolator(new BounceInterpolator());
TransitionManager.beginDelayedTransition(mRoot, transition);
int width = view2.getWidth();
int height = view2.getHeight();
int gap = 140;
Rect rect = new Rect(0, gap, width, height - gap);
if (rect.equals(view2.getClipBounds())) {
    view2.setClipBounds(null);
} else {
    view2.setClipBounds(rect);
}
```

最终效果：

![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0666b61f2db54ca8924178c37523010c~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeScroll

当调用 `view.scrollTo()` 会触发转场效果：

```java
ChangeScroll transition = new ChangeScroll();
transition.setInterpolator(new AnticipateOvershootInterpolator());
TransitionManager.beginDelayedTransition(mRoot, transition);
if (view1.getScrollX() == -100 && view1.getScrollY() == -100) {
    view1.scrollTo(0, 0);
} else {
    view1.scrollTo(-100, -100);
}
```

最终效果：

![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/f6d2910ebdde421c95ef1df93dc19bf1~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeTransform

这个就厉害了，View 的 `translation`、`scale` 和 `rotation` 发生改变时都会触发：

```java
ChangeTransform transition = new ChangeTransform();
transition.setInterpolator(new OvershootInterpolator());
TransitionManager.beginDelayedTransition(mRoot, transition);
if (view1.getTranslationX() == 100 && view1.getTranslationY() == 100) {
    view1.setTranslationX(0);
    view1.setTranslationY(0);
} else {
    view1.setTranslationX(100);
    view1.setTranslationY(100);
}
if (view2.getRotationX() == 30f) {
    view2.setRotationX(0);
} else {
    view2.setRotationX(30);
}
if (view3.getRotationY() == 30f) {
    view3.setRotationY(0);
} else {
    view3.setRotationY(30);
}
if (view4.getScaleX() == 0.5f && view4.getScaleY() == 0.5f) {
    view4.setScaleX(1f);
    view4.setScaleY(1f);
} else {
    view4.setScaleX(0.5f);
    view4.setScaleY(0.5f);
}
```

最终效果：

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/697dc92b19ad4b3f892a6ed7edf5a546~tplv-k3u1fbpfcp-zoom-1.image)

## 自定义 Transition

### 介绍

其实 `Transition` 的原理很简单，大致的逻辑如下：

1. 记录当前状态的属性值，比如位置大小或者自定义属性之类
2. 创建执行动画，参数为当前值和目标值，根据对应算法来完成动画效果
3. 根据目标状态的属性值和记录的缓存属性值，调用创建好的动画对象执行即可

那落实到代码中，首先先集成 `Transition` 类，会让你实现三个方法：`captureStartValues`、`captureEndValues`和`createAnimator`。

1. 定义你关心的**属性值**；

	官方建议属性定义的规则为：`package_name:transition_class:property_name`.

    比如
    ```java
    private static String PROPNAME_TEXT_COLOR = "xiaweizi:changeTextColor:color";
    ```
    我想在文本颜色发生改变时做转场动画，就可以定义上述的属性。

2. 记录起始状态的属性；

	```java
    void captureStartValues(TransitionValues transitionValues)
    void captureEndValues(TransitionValues transitionValues);
  ```
    上述方法分别存储起始状态下对应的属性值：

    ```java
    transitionValues.values.put(PROPNAME_TEXT_COLOR, view.getCurrentTextColor());
    ```
3. 创建动画；

	```java
    Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues)
  ```
    参数值的 `startValues`和`endValues`分别可以拿到你存储的属性值，之后创建动画并返回即可，后续系统会根据你创建的动画进行转场。


是不是很简单，接下来通过几个案例带大家感受一下：

### ChangeTextTransition

[ChangeTextTransition.java](https://github.com/xiaweizi/TransitionDemo/blob/master/app/src/main/java/com/xiaweizi/transitiondemo/transition/ChangeTextTransition.java) 该类中定义了：

```java
private static String PROPNAME_TEXT = "xiaweizi:changeText:text";
private static String PROPNAME_TEXT_COLOR = "xiaweizi:changeTextColor:color";
private static String PROPNAME_TEXT_SIZE = "xiaweizi:changeTextSize:size";
private static String PROPNAME_TEXT_LEVEL = "xiaweizi:changeTextTypeface:level";
```

分别代表文本内容变化、文本颜色变化、文本大小变化和文本字体变化。我们只挑一个文本颜色来看一下动画是如何实现的：

```java
// 记录下起始状态属性值
private void captureValues(TransitionValues transitionValues) {
    if (transitionValues == null || !(transitionValues.view instanceof TextView)) return;
    TextView view = (TextView) transitionValues.view;
    transitionValues.values.put(PROPNAME_TEXT, view.getText());
    transitionValues.values.put(PROPNAME_TEXT_COLOR, view.getCurrentTextColor());
    transitionValues.values.put(PROPNAME_TEXT_SIZE, view.getTextSize());
    transitionValues.values.put(PROPNAME_TEXT_LEVEL, view.getTag(R.id.type_face_level));
}

@Override
public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
    if (startValues == null || endValues == null) {
        return null;
    }
    if (!(endValues.view instanceof TextView)) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
    TextView endView = (TextView) endValues.view;
    int startTextColor = (int) startValues.values.get(PROPNAME_TEXT_COLOR);
    int endTextColor = (int) endValues.values.get(PROPNAME_TEXT_COLOR);
    ObjectAnimator animator = ObjectAnimator.ofArgb(endView, new TextColorProperty(), startTextColor, endTextColor);
    animator.setDuration(300);
    return animator;
}
```

看一下这四种属性发生变化时的效果：

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c12e4909cacc476db08c0a2f6377c887~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeBackgroundColorTransition

类似于文本颜色，只不过针对的是 `view.setBackground()`，主要的代码在于创建 `Animator`：

```java
@Override
public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
    if (startValues == null || endValues == null) {
        return null;
    }
    final View endView = endValues.view;
    ColorDrawable startColorDrawable = (ColorDrawable) startValues.values.get(PROPNAME_COLOR);
    ColorDrawable endColorDrawable = (ColorDrawable) endValues.values.get(PROPNAME_COLOR);
    if (startColorDrawable == null || endColorDrawable == null) return super.createAnimator(sceneRoot, startValues, endValues);
    final int startColor = startColorDrawable.getColor();
    final int endColor = endColorDrawable.getColor();
    ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
    animator.setDuration(300);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int animatedValue = (int) animation.getAnimatedValue();
            endView.setBackgroundColor(animatedValue);
        }
    });
    return animator;
}
```

最终效果：

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4f7b94172e6d4cebb6c7731a2567ccf6~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeImageResourceTransition

有的时候发现，在切换图片的时候过度会很生硬，那可以通过在对 `View` 的 `alpha` 属性从 1~0~1 的过程中替换图片，这样显得很平滑。

```java
@Override
public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
    if (startValues == null || endValues == null) {
        return null;
    }
    if (!(endValues.view instanceof ImageView)) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
    final ImageView endView = (ImageView) endValues.view;
    final Drawable startDrawable = (Drawable) startValues.values.get(PROPNAME_IMAGE_RESOURCE);
    final Drawable endDrawable = (Drawable) endValues.values.get(PROPNAME_IMAGE_RESOURCE);
    ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
    animator.setDuration(300);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float animatedValue = (float) animation.getAnimatedValue();
            if (animatedValue <= 0.5f) {
                endView.setImageDrawable(startDrawable);
                float ratio = (0.5f - animatedValue) / 0.5f;
                endView.setAlpha(ratio);
            } else {
                endView.setImageDrawable(endDrawable);
                float ratio = (animatedValue - 0.5f) / 0.5f;
                endView.setAlpha(ratio);
            }
        }
    });
    return animator;
```

最终效果：

![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/3d44e7a24e37449abe0699fdb3e831de~tplv-k3u1fbpfcp-zoom-1.image)

### ChangeCustomTransition

除了 `View` 原生的属性，自定义属性同样也可以。

创建 `Animator` 没什么区别：

```java
@Override
public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
    if (startValues == null || endValues == null) {
        return null;
    }
    if (!(endValues.view instanceof TransitionView)) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
    final TransitionView endView = (TransitionView) endValues.view;
    final float startRatio = (float) startValues.values.get(PROPNAME_CUSTOM_RATIO);
    final float endRatio = (float) endValues.values.get(PROPNAME_CUSTOM_RATIO);
    ObjectAnimator animator = ObjectAnimator.ofFloat(endView, "ratio", startRatio, endRatio);
    animator.setDuration(300);
    return animator;
}
```

主要在自定义 `View` 的绘制逻辑：

```java
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 绘制左边
    canvas.save();
    mRect.set(0, 0, (int) (getWidth() * mRatio), getHeight());
    canvas.clipRect(mRect);
    mTextPaint.setColor(mStartColor);
    TransitionUtils.drawTextCenter(canvas, "文本三", getWidth() / 2, getHeight() / 2, mTextPaint);
    canvas.restore();

    // 绘制右边
    canvas.save();
    mRect.set((int) (getWidth() * mRatio), 0, getWidth(), getHeight());
    canvas.clipRect(mRect);
    mTextPaint.setColor(mEndColor);
    TransitionUtils.drawTextCenter(canvas, "三本文", getWidth() / 2, getHeight() / 2, mTextPaint);
    canvas.restore();
}
```

最终的效果：

![](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/63ca8809c2504dd985e26eab665a1f22~tplv-k3u1fbpfcp-zoom-1.image)

## Scene

终于开始介绍文章开头的效果是如何实现的：

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/af74da88de0549e9bd0fb57ef9b14205~tplv-k3u1fbpfcp-zoom-1.image)

有了前面的基础铺垫，实现起来就很简单。

`Scene` 就是为这种场景的过度而设计，不需要关注过度过程，只需要传入前后的布局，并保证各个元素的 `id` 保持一致即可。

1. 创建前后 `layout`；`layout_scene1.xml` 和 `layout_scene2.xml` 具体代码就补贴了
2. 创建前后 `Scene` 对象；
	```java
    mScene1 = Scene.getSceneForLayout(mRoot, R.layout.layout_scene1, this);
    mScene2 = Scene.getSceneForLayout(mRoot, R.layout.layout_scene2, this);
  ```
3. 创建转场 `Transition`；我们把之前自定的组合成 `TransitionSet`：
	```java
    public class SceneTransition extends TransitionSet {
      public SceneTransition() {
          init();
      }
      public SceneTransition(Context context, AttributeSet attrs) {
          super(context, attrs);
          init();
      }
      private void init() {
          addTransition(new ChangeTextTransition())
                  .addTransition(new ChangeScroll())
                  .addTransition(new ChangeBackgroundColorTransition())
                  .addTransition(new ChangeBounds());
      }
	}
  ```
4. 开始切换场景；
	```java
    TransitionManager.go(mScene1, mTransition);
    TransitionManager.go(mScene2, mTransition);
  ```

## 总结

到此，先详细的和大家分享了系统自带的 `Transition`，并分析了其实现细节和原理，提供了多个自定义 `Transition`，接着了解了 `Scene` 创建过程，并通过简答的 `demo` 实现了从一个场景到另一个场景的过度效果，由浅入深，图文并茂，希望可以帮助到大家。


