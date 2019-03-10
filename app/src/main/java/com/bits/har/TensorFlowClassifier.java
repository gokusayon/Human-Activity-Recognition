package com.bits.har;

import android.content.Context;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.List;


class TensorFlowClassifier {
    static {
        System.loadLibrary("tensorflow_inference");
    }

    private TensorFlowInferenceInterface inferenceInterface;
    private static final String MODEL_FILE = "file:///android_asset/v5-1.pb";
    private static final String INPUT_NODE = "conv2d_4_input";
    private static final String[] OUTPUT_NODES = {"dense_12/Softmax"};
    private static final String OUTPUT_NODE = "dense_12/Softmax";
    private static final long[] INPUT_SIZE = {1, 180, 9, 1};
    private static final int OUTPUT_SIZE = 2;

    TensorFlowClassifier(final Context context) {
        inferenceInterface = new TensorFlowInferenceInterface(context.getAssets(), MODEL_FILE);
    }

    float[] predictProbabilities(float[] data) {
        float[] result = new float[OUTPUT_SIZE];
        inferenceInterface.feed(INPUT_NODE, data, INPUT_SIZE);
        inferenceInterface.run(OUTPUT_NODES);
        inferenceInterface.fetch(OUTPUT_NODE, result);

        //Downstairs	Jogging	  Sitting	Standing	Upstairs	Walking
        return result;
    }
}
