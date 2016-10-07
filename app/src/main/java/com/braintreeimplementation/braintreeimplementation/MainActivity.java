package com.braintreeimplementation.braintreeimplementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.PaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String clientToken;
    String jsonString = "{\"message\":\"Success\",\"userdata\":[{\"id\":15,\"subscription_name\":\"Monthly Subscription Plan\",\"amount\":10,\"country_code\":\"SGD\",\"country_region\":\"SG\",\"planid\":\"7422\",\"subs_month\":30,\"subsription_month\":\"1 Month\",\"subscription_desc\":\"Continue to enjoy access to all of our content after your free trial is over, at a flat rate of just $10 per month! It\\u2019s that simple.\",\"created\":\"2015-10-19T00:00:00+0000\",\"active\":0,\"signinexpire\":1,\"is_recurring\":\"0\",\"btcustomer_id\":\"0\",\"btstatus\":\"0\",\"cancelflag\":\"0\",\"paypaldate\":\"0\",\"paypaltranscation\":\"0\",\"buynow\":1,\"btsubscription_id\":\"0\"},{\"id\":19,\"subscription_name\":\"6-Month Free Trial\",\"amount\":0,\"country_code\":\"SGD\",\"country_region\":\"SG\",\"planid\":\"0\",\"subs_month\":\"180\",\"subsription_month\":\"6 Month\",\"subscription_desc\":\"Register an account with mm2view now and get a 6-month free trial! Enjoy unlimited access to all content during this period\",\"created\":\"2015-10-26T00:00:00+0000\",\"active\":0,\"signinexpire\":1,\"is_recurring\":\"0\",\"btcustomer_id\":\"0\",\"btstatus\":\"0\",\"cancelflag\":\"0\",\"paypaldate\":\"0\",\"paypaltranscation\":\"0\",\"buynow\":\"0\",\"btsubscription_id\":\"0\"}],\"messagedesc\":\"User Subscription information\",\"msgstatus\":\"OK\",\"code\":\"200\"}";
    private ViewPager mPager;
    TextView userName;
    private CirclePageIndicator indicator;
    ArrayList<SubscriptionData> subDataArray;
    private static int currentPage = 0;
    private String subscriptionID;
    private String transactionID;
    private String subscriptionPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BrainTree Payment");
        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Log.d("jsonObject",""+jsonObject);
            if (jsonObject.getString("code").equalsIgnoreCase("200")) {

                JSONArray jsonObjectData = (JSONArray) jsonObject.getJSONArray("userdata");

                subDataArray = new ArrayList<SubscriptionData>();
                for (int i = 0; i < jsonObjectData.length(); i++) {


                    SubscriptionData subData = new SubscriptionData();
                    JSONObject obj = (JSONObject) jsonObjectData.get(i);
                    subData.setAmount(obj.getString("amount"));
                    subData.setPeriod(obj.getString("subsription_month"));
                    subData.setPlan(obj.getString("subscription_name"));
                    subData.setDiscription(obj.getString("subscription_desc"));
                    subData.setSubscriptionID(obj.getString("id"));
                    subData.setCurrency(obj.getString("country_code"));
                    subData.setActive(obj.getString("active"));
                    subData.setBuynow(obj.getString("buynow"));
                    subData.setSigninexpire(obj.getString("signinexpire"));
                    subData.setisRecurring(obj.getString("is_recurring"));
                    subData.settransactionId(obj.getString("btsubscription_id"));
                    subDataArray.add(subData);
                    //setVideosInfo(subData, inflater, linearLayout);
                }
                // linearLayoutMain.addView(categoryView);
                //subcriptionList.setVisibility(View.VISIBLE);
                //SubscriptionsAdapter adapter = new SubscriptionsAdapter();
                int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20 * 2, getResources().getDisplayMetrics());
                mPager.setPageMargin(-margin);
                SubscriptionsAdapter subscriptionsAdapter = new SubscriptionsAdapter(MainActivity.this, subDataArray);
                mPager.setAdapter(subscriptionsAdapter);
                mPager.setOffscreenPageLimit(subscriptionsAdapter.getCount());
                //A little space between pages
                mPager.setPageMargin(15);

                //If hardware acceleration is enabled, you should also remove
                // clipping on the pager for its children.
                mPager.setClipChildren(false);
                indicator.setViewPager(mPager);

                final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
                indicator.setRadius(5 * density);

                // Pager listener over indicator
                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        currentPage = position;
                    }

                    @Override
                    public void onPageScrolled(int pos, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int pos) {

                    }
                });

            }
        } catch (Exception e) {
        }

    }

    private class SubscriptionsAdapter extends PagerAdapter {


        private int REQUEST_CODE = 200;
        private ArrayList<SubscriptionData> subDataArray;
        private LayoutInflater inflater;
        private Context context;

        @Override
        public int getCount() {
            return subDataArray.size();
        }

        public SubscriptionsAdapter(Context context, ArrayList<SubscriptionData> subDataArray) {
            this.context = context;
            this.subDataArray = subDataArray;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.93f;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.subscription_bind_layout, view, false);
            assert imageLayout != null;
            final SubscriptionData data = subDataArray.get(position);


            TextView tvHeader = (TextView) imageLayout.findViewById(R.id.textSubscriptionHeader);
            tvHeader.setText(data.getPeriod() + " Subscription");

            TextView tvPlan = (TextView) imageLayout.findViewById(R.id.textSubscriptionplan);
            tvPlan.setText(data.getPlan());

            LinearLayout overlay_layout = (LinearLayout) imageLayout.findViewById(R.id.overlay_layout_id);
            LinearLayout recurring_layout = (LinearLayout) imageLayout.findViewById(R.id.recurring_layout);


            TextView tvPDescription = (TextView) imageLayout.findViewById(R.id.textdiscription);
            tvPDescription.setText(data.getDiscription());

            TextView tvAmount = (TextView) imageLayout.findViewById(R.id.textAmount);
            tvAmount.setText(data.getAmount() + "  " + data.getCurrency());

            TextView recurringText = (TextView) imageLayout.findViewById(R.id.recurring_text_button);

            recurringText.setPaintFlags(recurringText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            recurringText.setText("click here");
//            SpannableString content = new SpannableString("Content");
//            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//            recurringText.setText(content);
            Button goButton = (Button) imageLayout.findViewById(R.id.gobutton);
            if (data.getSigninexpire().equalsIgnoreCase("1")) {

                if (data.getBuynow().equalsIgnoreCase("0")) {
                    goButton.setEnabled(false);
                }
            }
            if (data.getActive().equalsIgnoreCase("1")) {
                overlay_layout.setVisibility(View.VISIBLE);
                userName.setText("Hi, " + "User" + " You are a subscribed user");
                if (data.getisRecurring().equalsIgnoreCase("1")) {
                    recurring_layout.setVisibility(View.VISIBLE);
                }
            }
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    subscriptionID = data.getSubscriptionID();
                    subscriptionPlan = data.getPlan();
                    getTokenInfromation();


                }
            });

            view.addView(imageLayout, 0);

            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }




        private void getTokenInfromation() {
            clientToken = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiIxYzdkZjIxNWY0YzY0NWFiMTgwNzdmYjE3ODE5MTU5ODc3ODk3N2ZjYTRiMjQ2MWEzYWUyYzE5YTg1ZDYzN2YwfGNyZWF0ZWRfYXQ9MjAxNi0xMC0wNlQxMToyNTo0Mi41OTI2NjI2MzArMDAwMFx1MDAyNm1lcmNoYW50X2lkPTQycm02ajQ0bWRibWZ4OThcdTAwMjZwdWJsaWNfa2V5PWZmOG5odHdjazlxeDNxcTgiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvNDJybTZqNDRtZGJtZng5OC9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbImN2diJdLCJlbnZpcm9ubWVudCI6InNhbmRib3giLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvNDJybTZqNDRtZGJtZng5OC9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9jbGllbnQtYW5hbHl0aWNzLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20vNDJybTZqNDRtZGJtZng5OCJ9LCJ0aHJlZURTZWN1cmVFbmFibGVkIjp0cnVlLCJwYXlwYWxFbmFibGVkIjp0cnVlLCJwYXlwYWwiOnsiZGlzcGxheU5hbWUiOiJUZWNocGhhbnQgQ29uc3VsdGluZyBncm91cCIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6InNnZG1lcmNoYW50IiwiY3VycmVuY3lJc29Db2RlIjoiU0dEIn0sImNvaW5iYXNlRW5hYmxlZCI6ZmFsc2UsIm1lcmNoYW50SWQiOiI0MnJtNmo0NG1kYm1meDk4IiwidmVubW8iOiJvZmYifQ==";
            onBraintreeSubmit(clientToken);

        }

        public void onBraintreeSubmit(String clientToken) {
            PaymentRequest paymentRequest = new PaymentRequest()
                    .clientToken(clientToken);
            startActivityForResult(paymentRequest.getIntent(MainActivity.this), REQUEST_CODE);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
                        BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
                );
                String nonce = paymentMethodNonce.getNonce();
                Toast.makeText(MainActivity.this, "Nounce" +nonce, Toast.LENGTH_LONG).show();
            }
        }
    }

}
