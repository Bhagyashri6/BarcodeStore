package com.atminfotech.barcodestorenew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AboutPage extends Activity {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final View mView;
    private String mDescription;
    private int mImage = 0;
    private boolean mIsRTL = false;
    private Typeface mCustomFont;

    public AboutPage(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mView = mInflater.inflate(R.layout.about_page, null);
    }

    public AboutPage setCustomFont(String path) {
        mCustomFont = Typeface.createFromAsset(mContext.getAssets(), path);
        return this;
    }

    public AboutPage addEmail(String email) {
        Element emailElement = new Element();
        emailElement.setTitle(mContext.getString(R.string.about_contact_us));
        emailElement.setIcon(R.drawable.about_icon_email);
        emailElement.setColor(ContextCompat.getColor(mContext, R.color.about_item_icon_color));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailElement.setIntent(intent);

        addItem(emailElement);
        return this;
    }

    public AboutPage addRma(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.addRma));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0226594421"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }

    public AboutPage addTwitter(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.about_twitter1));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9619900785"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }
    public AboutPage addPlayStore(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.about_twitter2));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9664008090"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }
    public AboutPage addTwitterA(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.about_twitter3));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9619900785"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }
    public AboutPage addPlayStoreA(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.about_twitter4));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:9930699901"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }


    public AboutPage addAndroid(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.addAndroid));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:02265324421"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }




    public AboutPage addwins1(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.addwins1));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:02265124421"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }

    public AboutPage addwins2(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.addwins2));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:02265424421"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }
    public AboutPage addwins3(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.addwins3));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:02265524421"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }


    public AboutPage addweb(String id) {
        Element twitterElement = new Element();
        twitterElement.setTitle(mContext.getString(R.string.addweb));
        twitterElement.setIcon(R.drawable.ic_call);
        twitterElement.setColor(ContextCompat.getColor(mContext, R.color.about_twitter_color));
        twitterElement.setValue(id);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:02265624421"));
        twitterElement.setIntent(intent);
        addItem(twitterElement);

        return this;
    }



    public AboutPage addWebsite(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Element websiteElement = new Element();
        websiteElement.setTitle(mContext.getString(R.string.about_website));
        websiteElement.setIcon(R.drawable.about_icon_link);
        websiteElement.setColor(ContextCompat.getColor(mContext, R.color.about_item_icon_color));
        websiteElement.setValue(url);

        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

        websiteElement.setIntent(browserIntent);
        addItem(websiteElement);

        return this;
    }
    public AboutPage adddWebsite(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Element websiteElement = new Element();
        websiteElement.setTitle(mContext.getString(R.string.about_website));
        websiteElement.setIcon(R.drawable.about_icon_link);
        websiteElement.setColor(ContextCompat.getColor(mContext, R.color.about_item_icon_color));
        websiteElement.setValue(url);

        Uri uri = Uri.parse(url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

        websiteElement.setIntent(browserIntent);
        addItem(websiteElement);

        return this;
    }

    public AboutPage addItem(Element element) {
        LinearLayout wrapper = (LinearLayout) mView.findViewById(R.id.about_providers);
        wrapper.addView(createItem(element));
        wrapper.addView(getSeparator(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.about_separator_height)));
        return this;
    }

    public AboutPage setImage(int resource) {
        this.mImage = resource;
        return this;
    }

    public AboutPage addGroup(String name) {

        TextView textView = new TextView(mContext);
        textView.setText(name);
        TextViewCompat.setTextAppearance(textView,R.style.about_groupTextAppearance);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (mCustomFont != null) {
            textView.setTypeface(mCustomFont);
        }

        int padding = mContext.getResources().getDimensionPixelSize(R.dimen.about_group_text_padding);
        textView.setPadding(padding, padding, padding, padding);


        if (mIsRTL) {
            textView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            textParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        } else {
            textView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            textParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        }
        textView.setLayoutParams(textParams);

        ((LinearLayout) mView.findViewById(R.id.about_providers)).addView(textView);
        return this;
    }

    public AboutPage isRTL(boolean value) {
        this.mIsRTL = value;
        return this;
    }


    public View create() {
        TextView description = (TextView) mView.findViewById(R.id.description);

        ImageView image = (ImageView) mView.findViewById(R.id.image);
        image.setImageResource(mImage);

        if (!TextUtils.isEmpty(mDescription)) {
            description.setText(mDescription);
        }

        description.setGravity(Gravity.CENTER);

        if (mCustomFont != null) {
            description.setTypeface(mCustomFont);
        }

        return mView;
    }

    private View createItem(final Element element) {
        LinearLayout wrapper = new LinearLayout(mContext);
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setClickable(true);

        if (element.getOnClickListener() != null) {
            wrapper.setOnClickListener(element.getOnClickListener());
        } else if (element.getIntent() != null) {
            wrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mContext.startActivity(element.getIntent());
                    } catch (Exception e) {
                    }
                }
            });

        }

        TypedValue outValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, outValue, true);
        wrapper.setBackgroundResource(outValue.resourceId);

        int padding = mContext.getResources().getDimensionPixelSize(R.dimen.about_text_padding);
        wrapper.setPadding(padding, padding, padding, padding);
        LinearLayout.LayoutParams wrapperParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wrapper.setLayoutParams(wrapperParams);


        TextView textView = new TextView(mContext);
        TextViewCompat.setTextAppearance(textView, R.style.about_elementTextAppearance);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textParams);
        if (mCustomFont != null) {
            textView.setTypeface(mCustomFont);
        }

        ImageView iconView = null;

        if (element.getIcon() != null) {
            iconView = new ImageView(mContext);
            int size = mContext.getResources().getDimensionPixelSize(R.dimen.about_icon_size);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(size, size);
            iconView.setLayoutParams(iconParams);
            int iconPadding = mContext.getResources().getDimensionPixelSize(R.dimen.about_icon_padding);
            iconView.setPadding(iconPadding, 0, iconPadding, 0);

            if (Build.VERSION.SDK_INT < 21) {
                Drawable drawable = VectorDrawableCompat.create(iconView.getResources(), element.getIcon(), iconView.getContext().getTheme());
                iconView.setImageDrawable(drawable);
            } else {
                iconView.setImageResource(element.getIcon());
            }

            Drawable wrappedDrawable = DrawableCompat.wrap(iconView.getDrawable());
            wrappedDrawable = wrappedDrawable.mutate();
            if (element.getAutoIconColor()){
                if (element.getColor() != null) {
                    DrawableCompat.setTint(wrappedDrawable, element.getColor());
                } else {
                    DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(mContext, R.color.about_item_icon_color));
                }
            }

        } else {
            int iconPadding = mContext.getResources().getDimensionPixelSize(R.dimen.about_icon_padding);
            textView.setPadding(iconPadding, iconPadding, iconPadding, iconPadding);
        }


        textView.setText(element.getTitle());


        if (mIsRTL) {

            final int gravity = element.getGravity() != null ? element.getGravity() : Gravity.END;

            wrapper.setGravity(gravity | Gravity.CENTER_VERTICAL);
            //noinspection ResourceType
            textParams.gravity = gravity | Gravity.CENTER_VERTICAL;
            wrapper.addView(textView);
            if (element.getIcon() != null) {
                wrapper.addView(iconView);
            }

        } else {
            final int gravity = element.getGravity() != null ? element.getGravity() : Gravity.START;
            wrapper.setGravity(gravity | Gravity.CENTER_VERTICAL);
            //noinspection ResourceType
            textParams.gravity = gravity | Gravity.CENTER_VERTICAL;
            if (element.getIcon() != null) {
                wrapper.addView(iconView);
            }
            wrapper.addView(textView);
        }

        return wrapper;
    }

    private View getSeparator() {
        return mInflater.inflate(R.layout.about_page_separator, null);
    }
}
