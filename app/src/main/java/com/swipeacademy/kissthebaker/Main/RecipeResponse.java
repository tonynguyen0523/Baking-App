package com.swipeacademy.kissthebaker.Main;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyn on 7/25/2017.
 */

public class RecipeResponse implements Parcelable{

    /**
     * id : 1
     * name : Nutella Pie
     * ingredients : [{"quantity":2,"measure":"CUP","ingredient":"Graham Cracker crumbs"},{"quantity":6,"measure":"TBLSP","ingredient":"unsalted butter, melted"},{"quantity":0.5,"measure":"CUP","ingredient":"granulated sugar"},{"quantity":1.5,"measure":"TSP","ingredient":"salt"},{"quantity":5,"measure":"TBLSP","ingredient":"vanilla"},{"quantity":1,"measure":"K","ingredient":"Nutella or other chocolate-hazelnut spread"},{"quantity":500,"measure":"G","ingredient":"Mascapone Cheese(room temperature)"},{"quantity":1,"measure":"CUP","ingredient":"heavy cream(cold)"},{"quantity":4,"measure":"OZ","ingredient":"cream cheese(softened)"}]
     * steps : [{"id":0,"shortDescription":"RecipeResponse Introduction","description":"RecipeResponse Introduction","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4","thumbnailURL":""},{"id":1,"shortDescription":"Starting prep","description":"1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.","videoURL":"","thumbnailURL":""},{"id":2,"shortDescription":"Prep the cookie crust.","description":"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4","thumbnailURL":""},{"id":3,"shortDescription":"Press the crust into baking form.","description":"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4","thumbnailURL":""},{"id":4,"shortDescription":"Start filling prep","description":"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4","thumbnailURL":""},{"id":5,"shortDescription":"Finish filling prep","description":"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.","videoURL":"","thumbnailURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4"},{"id":6,"shortDescription":"Finishing Steps","description":"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4","thumbnailURL":""}]
     * servings : 8
     * image :
     */

    private int id;
    private String name;
    private int servings;
    private String image;
    private ArrayList<IngredientsBean> ingredients;
    private ArrayList<StepsBean> steps;

    public RecipeResponse() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredientsList",ingredients);
        bundle.putParcelableArrayList("stepsList", steps);
        dest.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<RecipeResponse> CREATOR = new Parcelable.Creator<RecipeResponse>() {
        @Override
        public RecipeResponse createFromParcel(Parcel in) {
            RecipeResponse recipeResponse = new RecipeResponse();
            recipeResponse.id = in.readInt();
            recipeResponse.image = in.readString();
            recipeResponse.name = in.readString();
            recipeResponse.servings = in.readInt();
            Bundle bundle = in.readBundle(IngredientsBean.class.getClassLoader());
            Bundle bundle1 = in.readBundle(StepsBean.class.getClassLoader());
            recipeResponse.ingredients = bundle.getParcelableArrayList("ingredientsList");
            recipeResponse.steps = bundle1.getParcelableArrayList("stepsList");
            return recipeResponse;
        }

        @Override
        public RecipeResponse[] newArray(int size) {
            return new RecipeResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<IngredientsBean> getIngredients() {
        return ingredients;
    }


    public ArrayList<StepsBean> getSteps() {
        return steps;
    }


    public static class IngredientsBean implements Parcelable{
        /**
         * quantity : 2
         * measure : CUP
         * ingredient : Graham Cracker crumbs
         */

        private double quantity;
        private String measure;
        private String ingredient;


        protected IngredientsBean(Parcel in) {
            quantity = in.readDouble();
            measure = in.readString();
            ingredient = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(quantity);
            dest.writeString(measure);
            dest.writeString(ingredient);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<IngredientsBean> CREATOR = new Creator<IngredientsBean>() {
            @Override
            public IngredientsBean createFromParcel(Parcel in) {
                return new IngredientsBean(in);
            }

            @Override
            public IngredientsBean[] newArray(int size) {
                return new IngredientsBean[size];
            }
        };

        public double getQuantity() {
            return quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }
    }

    public static class StepsBean implements Parcelable{
        /**
         * id : 0
         * shortDescription : RecipeResponse Introduction
         * description : RecipeResponse Introduction
         * videoURL : https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4
         * thumbnailURL :
         */

        private int id;
        private String shortDescription;
        private String description;
        private String videoURL;
        private String thumbnailURL;

        protected StepsBean(Parcel in) {
            id = in.readInt();
            shortDescription = in.readString();
            description = in.readString();
            videoURL = in.readString();
            thumbnailURL = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(shortDescription);
            dest.writeString(description);
            dest.writeString(videoURL);
            dest.writeString(thumbnailURL);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<StepsBean> CREATOR = new Creator<StepsBean>() {
            @Override
            public StepsBean createFromParcel(Parcel in) {
                return new StepsBean(in);
            }

            @Override
            public StepsBean[] newArray(int size) {
                return new StepsBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }
    }
}
