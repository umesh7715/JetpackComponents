package com.boisneyphilippe.githubarchitecturecomponents.bindingUtils;

import java.util.Random;

public class PostBindingUtils {

    public static String capitalize(String text) {
        return text.toUpperCase();
    }

    public static String appendRandomNumberAndCapitalize(String text) {
        return text.toUpperCase() + new Random().nextInt(100);
    }
}
