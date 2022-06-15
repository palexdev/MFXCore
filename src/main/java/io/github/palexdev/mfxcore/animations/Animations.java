package io.github.palexdev.mfxcore.animations;

import io.github.palexdev.mfxcore.base.beans.AnimationsData;
import javafx.animation.*;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.WritableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Utility class to easily build animations of any sort. Designed with fluent api.
 */
public class Animations {

	//================================================================================
	// Constructors
	//================================================================================
	private Animations() {}

	//================================================================================
	// Static Methods
	//================================================================================

	/**
	 * Temporarily disables the given node for the specified duration.
	 */
	public static void disableTemporarily(Duration duration, Node node) {
		node.setDisable(true);
		Animations.PauseBuilder.build()
				.setOnFinished(event -> node.setDisable(false))
				.setDuration(duration)
				.getAnimation()
				.play();
	}

	/**
	 * Calls {@link #disableTemporarily(Duration, Node)} by converting the given millis value
	 * with {@link Duration#millis(double)}.
	 */
	public static void disableTemporarily(double millis, Node node) {
		disableTemporarily(Duration.millis(millis), node);
	}

	/**
	 * Executes the given onFinished action after the specified duration of time.
	 * (Uses a PauseTransition)
	 */
	public static void executeLater(Duration duration, EventHandler<ActionEvent> onFinished) {
		PauseBuilder.build().setDuration(duration).setOnFinished(onFinished).getAnimation().play();
	}

	/**
	 * Calls {@link #executeLater(Duration, EventHandler)} by converting the given millis value
	 * with {@link Duration#millis(double)}.
	 */
	public static void executeLater(double millis, EventHandler<ActionEvent> onFinished) {
		executeLater(Duration.millis(millis), onFinished);
	}

	/**
	 * Sets the text of the given {@link Labeled} with a fade out/fade in transition.
	 *
	 * @param labeled  the labeled control to change the text to
	 * @param duration the fade in and fade out speed
	 * @param nexText  the new text to set
	 * @return an instance of {@link AbstractBuilder}
	 */
	public static AbstractBuilder transitionText(Labeled labeled, Duration duration, String nexText) {
		return SequentialBuilder.build()
				.hide(AnimationsData.of(labeled, duration, event -> labeled.setText(nexText)))
				.show(AnimationsData.of(labeled, duration));
	}

	/**
	 * Calls {@link #transitionText(Labeled, Duration, String)} by converting the given millis value
	 * with {@link Duration#millis(double)}.
	 */
	public static AbstractBuilder transitionText(Labeled labeled, double millis, String nexText) {
		return transitionText(labeled, Duration.millis(millis), nexText);
	}

	/**
	 * Sets the text of the given {@link Text} with a fade out/fade in transition.
	 *
	 * @param text     the text control to change the text to
	 * @param duration the fade in and fade out speed
	 * @param nexText  the new text to set
	 * @return an instance of {@link AbstractBuilder}
	 */
	public static AbstractBuilder transitionText(Text text, Duration duration, String nexText) {
		return SequentialBuilder.build()
				.hide(AnimationsData.of(text, duration, event -> text.setText(nexText)))
				.show(AnimationsData.of(text, duration));
	}

	/**
	 * Calls {@link #transitionText(Text, Duration, String)} by converting the given millis value
	 * with {@link Duration#millis(double)}.
	 */
	public static AbstractBuilder transitionText(Text text, double millis, String nexText) {
		return transitionText(text, Duration.millis(millis), nexText);
	}

	/**
	 * @return true if the given animation status is RUNNING, otherwise false
	 */
	public static boolean isPlaying(Animation animation) {
		return animation.getStatus() == Animation.Status.RUNNING;
	}

	/**
	 * @return true if the given animation status is PAUSED, otherwise false
	 */
	public static boolean isPaused(Animation animation) {
		return animation.getStatus() == Animation.Status.PAUSED;
	}

	public static boolean isStopped(Animation animation) {
		return animation.getStatus() == Animation.Status.STOPPED;
	}

	//================================================================================
	// Builders
	//================================================================================

	/**
	 * Common base class for {@link ParallelBuilder} and {@link SequentialBuilder}.
	 * <p></p>
	 * This builder, designed with fluent api, allows you to create simple and complex animations with just a few lines of code.
	 * <p></p>
	 * The builder keeps the reference of the "main" animation (depending on the subclass can be ParallelTransition or SequentialTransition, in
	 * the AbstractBuilder the type is a generic {@link Animation}), and defines and abstract method that subclasses must implement
	 * to properly add animations to the "main".
	 */
	public static abstract class AbstractBuilder {
		//================================================================================
		// Properties
		//================================================================================
		protected Animation animation;

		//================================================================================
		// Abstract Methods
		//================================================================================

		/**
		 * Adds the given animation to the "main" animation.
		 */
		protected abstract void addAnimation(Animation animation);

		/**
		 * @return the "main" animation instance
		 */
		public abstract Animation getAnimation();

		//================================================================================
		// Methods
		//================================================================================
		protected void init(Animation animation) {
			this.animation = animation;
		}

		/**
		 * Adds the given animation to the "main" animation by calling {@link #addAnimation(Animation)}.
		 */
		public AbstractBuilder add(Animation animation) {
			addAnimation(animation);
			return this;
		}

		/**
		 * Sets the given onFinished action to the given animation and then adds it to the
		 * "main" animation by calling {@link #addAnimation(Animation)}.
		 */
		public AbstractBuilder add(Animation animation, EventHandler<ActionEvent> onFinished) {
			animation.setOnFinished(onFinished);
			addAnimation(animation);
			return this;
		}

		/**
		 * Gets the animation from the supplier and adds it to the "main" animation by calling {@link #addAnimation(Animation)}.
		 */
		public AbstractBuilder add(Supplier<Animation> animationSupplier) {
			addAnimation(animationSupplier.get());
			return this;
		}

		/**
		 * Gets the animation from the supplier, sets the given onFinished action to it and then adds it to the
		 * "main" animation by calling {@link #addAnimation(Animation)}.
		 */
		public AbstractBuilder add(Supplier<Animation> animationSupplier, EventHandler<ActionEvent> onFinished) {
			Animation animation = animationSupplier.get();
			animation.setOnFinished(onFinished);
			addAnimation(animation);
			return this;
		}

		/**
		 * Builds a {@link Timeline} with the given keyframes and adds it to the "main" animation by calling {@link #addAnimation(Animation)}.
		 */
		public AbstractBuilder add(KeyFrame... keyFrames) {
			addAnimation(new Timeline(keyFrames));
			return this;
		}

		/**
		 * Builds a {@link Timeline} with the given keyframes, sets the given onFinished action to it and then adds it to the
		 * "main" animation by calling {@link #addAnimation(Animation)}.
		 */
		public AbstractBuilder add(EventHandler<ActionEvent> onFinished, KeyFrame... keyFrames) {
			addAnimation(TimelineBuilder.build().add(keyFrames).setOnFinished(onFinished).getAnimation());
			return this;
		}


		/**
		 * For each given node builds and adds an animation that disables the node
		 * after the given duration of time.
		 *
		 * @param duration the time after which the nodes are disabled
		 */
		public AbstractBuilder disable(Duration duration, Node... nodes) {
			for (Node node : nodes) {
				addAnimation(
						PauseBuilder.build()
								.setDuration(duration)
								.setOnFinished(end -> node.setDisable(true))
								.getAnimation()
				);
			}
			return this;
		}

		/**
		 * For each given node builds and adds an animation that enables the node
		 * after the given duration of time.
		 *
		 * @param duration the duration after which the nodes are enabled
		 */
		public AbstractBuilder enable(Duration duration, Node... nodes) {
			for (Node node : nodes) {
				addAnimation(
						PauseBuilder.build()
								.setDuration(duration)
								.setOnFinished(end -> node.setDisable(false))
								.getAnimation()
				);
			}
			return this;
		}

		/**
		 * For each given window builds and adds an animation that hides the window by fading it out.
		 *
		 * @param duration the fade animation speed
		 */
		public AbstractBuilder hide(Duration duration, Window... windows) {
			for (Window window : windows) {
				addAnimation(TimelineBuilder.build().add(KeyFrames.of(duration, window.opacityProperty(), 0)).getAnimation());
			}
			return this;
		}

		/**
		 * Calls {@link #hide(Duration, Window...)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public AbstractBuilder hide(double millis, Window... windows) {
			return hide(Duration.millis(millis), windows);
		}

		/**
		 * For each given node builds and adds an animation that hides the node by fading it out.
		 *
		 * @param duration the fade animation speed
		 */
		public AbstractBuilder hide(Duration duration, Node... nodes) {
			for (Node node : nodes) {
				addAnimation(TimelineBuilder.build().hide(duration, node).getAnimation());
			}
			return this;
		}

		/**
		 * Calls {@link #hide(Duration, Node...)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public AbstractBuilder hide(double millis, Node... nodes) {
			return hide(Duration.millis(millis), nodes);
		}

		/**
		 * Creates and adds a fade out animation for each given {@link AnimationsData}.
		 */
		public final AbstractBuilder hide(AnimationsData... data) {
			for (AnimationsData animData : data) {
				addAnimation(TimelineBuilder.build().hide(animData).getAnimation());
			}
			return this;
		}

		/**
		 * For each given window builds and adds an animation that shows the window by fading it in.
		 *
		 * @param duration the fade animation speed
		 */
		public AbstractBuilder show(Duration duration, Window... windows) {
			for (Window window : windows) {
				addAnimation(TimelineBuilder.build().show(duration, window).getAnimation());
			}
			return this;
		}

		/**
		 * Calls {@link #show(Duration, Window...)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public AbstractBuilder show(double millis, Window... windows) {
			return show(Duration.millis(millis), windows);
		}

		/**
		 * For each given node builds and adds an animation that shows the node by fading it in.
		 *
		 * @param duration the fade animation speed
		 */
		public AbstractBuilder show(Duration duration, Node... nodes) {
			for (Node node : nodes) {
				addAnimation(TimelineBuilder.build().show(duration, node).getAnimation());
			}
			return this;
		}

		/**
		 * Calls {@link #show(Duration, Node...)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public AbstractBuilder show(double millis, Node... nodes) {
			return show(Duration.millis(millis), nodes);
		}

		/**
		 * Creates and adds a fade in animation for each given {@link AnimationsData}.
		 */
		public final AbstractBuilder show(AnimationsData... data) {
			for (AnimationsData animData : data) {
				addAnimation(TimelineBuilder.build().show(animData).getAnimation());
			}
			return this;
		}

		/**
		 * Sets the action to perform when the "main" animation ends.
		 */
		public AbstractBuilder setOnFinished(EventHandler<ActionEvent> onFinished) {
			animation.setOnFinished(onFinished);
			return this;
		}

		/**
		 * Sets the "main" animation cycle count.
		 */
		public AbstractBuilder setCycleCount(int cycleCount) {
			animation.setCycleCount(cycleCount);
			return this;
		}

		/**
		 * Sets the "main" animation delay.
		 */
		public AbstractBuilder setDelay(Duration delay) {
			animation.setDelay(delay);
			return this;
		}

		/**
		 * Sets the "main" animation delay.
		 */
		public AbstractBuilder setDelay(double millis) {
			animation.setDelay(Duration.millis(millis));
			return this;
		}

		/**
		 * Sets the "main" animation rate/speed.
		 */
		public AbstractBuilder setRate(double rate) {
			animation.setRate(rate);
			return this;
		}
	}

	/**
	 * Implementation of {@link AbstractBuilder} that uses a {@link SequentialTransition} as "main" animation.
	 */
	public static class SequentialBuilder extends AbstractBuilder {
		//================================================================================
		// Properties
		//================================================================================
		private final SequentialTransition sequentialTransition = new SequentialTransition();

		//================================================================================
		// Constructors
		//================================================================================
		public SequentialBuilder() {
			init(sequentialTransition);
		}

		//================================================================================
		// Static Methods
		//================================================================================

		/**
		 * @return a new SequentialBuilder instance. Equivalent to calling the constructor,
		 * it's just a way to omit the new keyword
		 */
		public static SequentialBuilder build() {
			return new SequentialBuilder();
		}

		//================================================================================
		// Override Methods
		//================================================================================
		@Override
		protected void addAnimation(Animation animation) {
			sequentialTransition.getChildren().add(animation);
		}

		@Override
		public SequentialTransition getAnimation() {
			return sequentialTransition;
		}
	}

	/**
	 * Implementation of {@link AbstractBuilder} that uses a {@link ParallelTransition} as "main" animation.
	 */
	public static class ParallelBuilder extends AbstractBuilder {
		//================================================================================
		// Properties
		//================================================================================
		private final ParallelTransition parallelTransition = new ParallelTransition();

		//================================================================================
		// Constructors
		//================================================================================
		public ParallelBuilder() {
			init(parallelTransition);
		}

		//================================================================================
		// Static Methods
		//================================================================================

		/**
		 * @return a new ParallelBuilder instance. Equivalent to calling the constructor,
		 * it's just a way to omit the new keyword
		 */
		public static ParallelBuilder build() {
			return new ParallelBuilder();
		}

		//================================================================================
		// Override Methods
		//================================================================================
		@Override
		protected void addAnimation(Animation animation) {
			parallelTransition.getChildren().add(animation);
		}

		@Override
		public ParallelTransition getAnimation() {
			return parallelTransition;
		}
	}

	/**
	 * Builder class to easily create a {@link Timeline} with fluent api.
	 */
	public static class TimelineBuilder {
		//================================================================================
		// Properties
		//================================================================================
		private final Timeline timeline = new Timeline();

		/**
		 * @return a new TimelineBuilder instance. Equivalent to calling the constructor,
		 * it's just a way to omit the new keyword
		 */
		public static TimelineBuilder build() {
			return new TimelineBuilder();
		}

		//================================================================================
		// Methods
		//================================================================================

		/**
		 * Adds the specified KeyFrames to the timeline.
		 */
		public TimelineBuilder add(KeyFrame... keyFrames) {
			timeline.getKeyFrames().addAll(Arrays.asList(keyFrames));
			return this;
		}

		/**
		 * Builds a KeyFrame to hide the given Window by fading it out.
		 *
		 * @param duration the fade animation speed
		 */
		public TimelineBuilder hide(Duration duration, Window window) {
			add(KeyFrames.of(duration, window.opacityProperty(), 0));
			return this;
		}

		/**
		 * Calls {@link #hide(Duration, Window)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public TimelineBuilder hide(double millis, Window window) {
			return hide(Duration.millis(millis), window);
		}

		/**
		 * Builds the KeyFrames to hide the given node by fading it out.
		 *
		 * @param duration the fade animation speed
		 */
		public TimelineBuilder hide(Duration duration, Node node) {
			add(
					KeyFrames.of(Duration.ZERO, node.opacityProperty(), 1.0, AnimationFactory.INTERPOLATOR_V1),
					KeyFrames.of(duration, node.opacityProperty(), 0, AnimationFactory.INTERPOLATOR_V1)
			);
			return this;
		}

		/**
		 * Calls {@link #hide(Duration, Node)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public TimelineBuilder hide(double millis, Node node) {
			return hide(Duration.millis(millis), node);
		}

		/**
		 * Builds the KeyFrames to hide the specified node in the AnimationsData bean, by fading it out.
		 */
		public final TimelineBuilder hide(AnimationsData data) {
			add(
					KeyFrames.of(Duration.ZERO, data.getNode().opacityProperty(), 1.0),
					KeyFrames.of(data.getDuration(), data.getNode().opacityProperty(), 0.0)
			);
			setOnFinished(data.getOnFinished());
			return this;
		}

		/**
		 * Builds a KeyFrame to show the given Window by fading it in.
		 *
		 * @param duration the fade animation speed
		 */
		public TimelineBuilder show(Duration duration, Window window) {
			add(KeyFrames.of(duration, window.opacityProperty(), 1.0));
			return this;
		}

		/**
		 * Calls {@link #show(Duration, Window)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public TimelineBuilder show(double millis, Window window) {
			return show(Duration.millis(millis), window);
		}

		/**
		 * Builds the KeyFrames to show the given node by fading it in.
		 *
		 * @param duration the fade animation speed
		 */
		public TimelineBuilder show(Duration duration, Node node) {
			add(
					KeyFrames.of(Duration.ZERO, node.opacityProperty(), 0.0),
					KeyFrames.of(duration, node.opacityProperty(), 1.0)
			);
			return this;
		}

		/**
		 * Calls {@link #show(Duration, Node)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public TimelineBuilder show(double millis, Node node) {
			return show(Duration.millis(millis), node);
		}

		/**
		 * Creates and adds a fade in animation for each given {@link AnimationsData}.
		 */
		public final TimelineBuilder show(AnimationsData data) {
			add(
					KeyFrames.of(Duration.ZERO, data.getNode().opacityProperty(), 0.0),
					KeyFrames.of(data.getDuration(), data.getNode().opacityProperty(), 1.0)
			);
			setOnFinished(data.getOnFinished());
			return this;
		}

		/**
		 * Sets the timeline cycle count.
		 */
		public TimelineBuilder setCycleCount(int cycleCount) {
			timeline.setCycleCount(cycleCount);
			return this;
		}

		/**
		 * Sets the timeline delay.
		 */
		public TimelineBuilder setDelay(Duration delay) {
			timeline.setDelay(delay);
			return this;
		}

		/**
		 * Sets the timeline delay.
		 */
		public TimelineBuilder setDelay(double millis) {
			timeline.setDelay(Duration.millis(millis));
			return this;
		}

		/**
		 * Sets the timeline rate/speed.
		 */
		public TimelineBuilder setRate(double rate) {
			timeline.setRate(rate);
			return this;
		}

		/**
		 * Sets the action to perform when the timeline ends.
		 */
		public TimelineBuilder setOnFinished(EventHandler<ActionEvent> onFinished) {
			timeline.setOnFinished(onFinished);
			return this;
		}

		/**
		 * @return the instance of the Timeline
		 */
		public Timeline getAnimation() {
			return timeline;
		}
	}

	/**
	 * Builder class to easily create a {@link PauseTransition} with fluent api.
	 */
	public static class PauseBuilder {
		//================================================================================
		// Properties
		//================================================================================
		private final PauseTransition pauseTransition = new PauseTransition();

		//================================================================================
		// Static Methods
		//================================================================================

		/**
		 * @return a new PauseBuilder instance. Equivalent to calling the constructor,
		 * it's just a way to omit the new keyword
		 */
		public static PauseBuilder build() {
			return new PauseBuilder();
		}

		//================================================================================
		// Methods
		//================================================================================

		public Animations.PauseBuilder setDelay(Duration duration) {
			pauseTransition.setDelay(duration);
			return this;
		}

		public Animations.PauseBuilder setDelay(double millis) {
			pauseTransition.setDelay(Duration.millis(millis));
			return this;
		}

		/**
		 * Sets the pause transition duration.
		 */
		public PauseBuilder setDuration(Duration duration) {
			pauseTransition.setDuration(duration);
			return this;
		}

		/**
		 * Calls {@link #setDuration(Duration)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public PauseBuilder setDuration(double millis) {
			pauseTransition.setDuration(Duration.millis(millis));
			return this;
		}

		/**
		 * Sets the action to perform when the pause transition ends.
		 */
		public PauseBuilder setOnFinished(EventHandler<ActionEvent> onFinished) {
			pauseTransition.setOnFinished(onFinished);
			return this;
		}

		/**
		 * @return the instance of the PauseTransition
		 */
		public PauseTransition getAnimation() {
			return pauseTransition;
		}

		/**
		 * This method can be considered an utility.
		 * <p></p>
		 * A {@link PauseTransition} with the previously set duration runs while the given boolean boolean expression
		 * is false. When the expression is evaluated and it is false the given retryAction is run and the transition
		 * is restarted. When it ends the expression is re-evaluated. When the expression becomes true the onSuccessAction is run.
		 * <p></p>
		 * So you have a {@link PauseTransition} that runs every tot unit of time and stops only when the given expression is true.
		 *
		 * @param booleanExpression the expression to check at a fixed time rate
		 * @param retryAction       the action to perform when the expression is false
		 * @param onSuccessAction   the action to perform when the expression is true
		 */
		public void runWhile(BooleanExpression booleanExpression, Runnable retryAction, Runnable onSuccessAction) {
			setOnFinished(event -> {
				if (!booleanExpression.get()) {
					retryAction.run();
					getAnimation().playFromStart();
				} else {
					onSuccessAction.run();
				}
			});
			getAnimation().play();
		}

		/**
		 * Same method as {@link #runWhile(BooleanExpression, Runnable, Runnable)} but instead of running
		 * until the given expression is true, it is limited to a maximum number of retries.
		 *
		 * @param maxRetryCount the max number of times the transition can be restarted
		 */
		public void runWhile(BooleanExpression booleanExpression, Runnable retryAction, Runnable onSuccessAction, int maxRetryCount) {
			AtomicInteger retryCount = new AtomicInteger(0);
			setOnFinished(event -> {
				if (!booleanExpression.get() && retryCount.get() < maxRetryCount) {
					retryCount.getAndIncrement();
					retryAction.run();
					getAnimation().playFromStart();
				} else {
					onSuccessAction.run();
				}
			});
			getAnimation().play();
		}
	}

	/**
	 * Builder class for keyframes and keyvalues.
	 */
	public static class KeyFrames {

		//================================================================================
		// Constructors
		//================================================================================
		private KeyFrames() {
		}

		//================================================================================
		// Static Methods
		//================================================================================

		/**
		 * Returns a new KeyFrame with the given duration and action.
		 */
		public static KeyFrame of(Duration duration, EventHandler<ActionEvent> action) {
			return new KeyFrame(duration, action);
		}

		/**
		 * Calls {@link #of(Duration, EventHandler)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public static KeyFrame of(double millis, EventHandler<ActionEvent> action) {
			return of(Duration.millis(millis), action);
		}

		/**
		 * Returns a new KeyFrame with the given duration and keyvalues.
		 */
		public static KeyFrame of(Duration duration, KeyValue... keyValues) {
			return new KeyFrame(duration, keyValues);
		}

		/**
		 * Calls {@link #of(Duration, KeyValue[])} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public static KeyFrame of(double millis, KeyValue... keyValues) {
			return of(Duration.millis(millis), keyValues);
		}

		/**
		 * Returns a new KeyFrame with the given duration and builds a new KeyValue for it
		 * with the given writable property and endValue.
		 */
		public static <T> KeyFrame of(Duration duration, WritableValue<T> writableValue, T endValue) {
			return of(duration, new KeyValue(writableValue, endValue));
		}

		/**
		 * Calls {@link #of(Duration, WritableValue, Object)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public static <T> KeyFrame of(double millis, WritableValue<T> writableValue, T endValue) {
			return of(Duration.millis(millis), writableValue, endValue);
		}

		/**
		 * Returns a new KeyFrame with the given duration and builds a new KeyValue for it
		 * with the given writable property, endValue and interpolator.
		 */
		public static <T> KeyFrame of(Duration duration, WritableValue<T> writableValue, T endValue, Interpolator interpolator) {
			return of(duration, new KeyValue(writableValue, endValue, interpolator));
		}

		/**
		 * Calls {@link #of(Duration, WritableValue, Object, Interpolator)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public static <T> KeyFrame of(double millis, WritableValue<T> writableValue, T endValue, Interpolator interpolator) {
			return of(Duration.millis(millis), writableValue, endValue, interpolator);
		}

		/**
		 * Returns a new KeyFrame with the given duration and builds a new KeyValue for it
		 * with the given writable property, endValue and interpolator.
		 */
		public static <T> KeyFrame of(Duration duration, WritableValue<T> writableValue, T endValue, Interpolators interpolator) {
			return of(duration, new KeyValue(writableValue, endValue, interpolator.toInterpolator()));
		}

		/**
		 * Calls {@link #of(Duration, WritableValue, Object, Interpolators)} by converting the given millis value
		 * with {@link Duration#millis(double)}.
		 */
		public static <T> KeyFrame of(double millis, WritableValue<T> writableValue, T endValue, Interpolators interpolator) {
			return of(Duration.millis(millis), writableValue, endValue, interpolator.toInterpolator());
		}
	}

	//================================================================================
	// Cubic Bezier implementation from https://github.com/gre/bezier-easing
	//================================================================================

	/**
	 * Cubic Bezier implementation from <a href="https://github.com/gre/bezier-easing">https://github.com/gre/bezier-easing</a>
	 * <p>
	 * Make sure to check this tool out! <a href="https://cubic-bezier.com/#0,0,.58,1">https://cubic-bezier.com</a>
	 */
	public static class BezierEasing {
		private static final int NEWTON_ITERATIONS = 4;
		private static final double NEWTON_MIN_SLOPE = 0.001;
		private static final double SUBDIVISION_PRECISION = 0.0000001;
		private static final int SUBDIVISION_MAX_ITERATIONS = 10;

		private static final int kSplineTableSize = 11;
		private static final double kSampleStepSize = 1.0 / (kSplineTableSize - 1.0);

		public static final Interpolator EASE = toInterpolator(.25, 1, .25, 1);
		public static final Interpolator LINEAR = toInterpolator(0, 0, 1, 1);
		public static final Interpolator EASE_IN = toInterpolator(.42, 0, 1, 1);
		public static final Interpolator EASE_OUT = toInterpolator(0, 0, .58, 1);
		public static final Interpolator EASE_BOTH = toInterpolator(.42, 0, .58, 1);

		private BezierEasing() {}

		private static double a(double a1, double a2) {
			return 1.0 - 3.0 * a2 + 3.0 * a1;
		}

		private static double b(double a1, double a2) {
			return 3.0 * a2 - 6.0 * a1;
		}

		private static double c(double a1) {
			return 3.0 * a1;
		}

		private static double calcBezier(double t, double a1, double a2) {
			return ((a(a1, a2) * t + b(a1, a2)) * t + c(a1)) * t;
		}

		// Returns dx/dt given t, x1, and x2, or dy/dt given t, y1, and y2.
		private static double getSlope(double aT, double aA1, double aA2) {
			return 3.0 * a(aA1, aA2) * aT * aT + 2.0 * b(aA1, aA2) * aT + c(aA1);
		}

		private static double binarySubdivide(double aX, double aA, double aB, double mX1, double mX2) {
			double currentX, currentT, i = 0;
			do {
				currentT = aA + (aB - aA) / 2.0;
				currentX = calcBezier(currentT, mX1, mX2) - aX;
				if (currentX > 0.0) {
					aB = currentT;
				} else {
					aA = currentT;
				}
			} while (Math.abs(currentX) > SUBDIVISION_PRECISION && ++i < SUBDIVISION_MAX_ITERATIONS);
			return currentT;
		}

		private static double newtonRaphsonIterate(double aX, double aGuessT, double mX1, double mX2) {
			for (var i = 0; i < NEWTON_ITERATIONS; ++i) {
				var currentSlope = getSlope(aGuessT, mX1, mX2);
				if (currentSlope == 0.0) {
					return aGuessT;
				}
				var currentX = calcBezier(aGuessT, mX1, mX2) - aX;
				aGuessT -= currentX / currentSlope;
			}
			return aGuessT;
		}

		public static double bezier(double frac, double x1, double y1, double x2, double y2) {
			double[] values = new double[kSplineTableSize];
			for (int i = 0; i < values.length; ++i) {
				values[i] = calcBezier(i * kSampleStepSize, x1, x2);
			}

			double intervalStart = 0.0;
			int currentSample = 1;
			double lastSample = kSplineTableSize - 1;

			for (; currentSample != lastSample && values[currentSample] <= frac; ++currentSample) {
				intervalStart += kSampleStepSize;
			}
			--currentSample;

			// Interpolate to provide an initial guess for t
			double dist = (frac - values[currentSample]) / (values[currentSample + 1] - values[currentSample]);
			double guessForT = intervalStart + dist * kSampleStepSize;

			double initialSlope = getSlope(guessForT, x1, x2);
			double fracToT;
			if (initialSlope >= NEWTON_MIN_SLOPE) {
				fracToT = newtonRaphsonIterate(frac, guessForT, x1, x2);
			} else if (initialSlope == 0.0) {
				fracToT = guessForT;
			} else {
				fracToT = binarySubdivide(frac, intervalStart, intervalStart + kSampleStepSize, x1, x2);
			}

			return calcBezier(fracToT, y1, y2);
		}

		public static Interpolator toInterpolator(double x1, double y1, double x2, double y2) {
			return new Interpolator() {
				@Override
				protected double curve(double t) {
					return bezier(t, x1, y1, x2, y2);
				}
			};
		}
	}
}

