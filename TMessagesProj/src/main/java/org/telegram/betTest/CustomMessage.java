package org.telegram.betTest;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.AlertDialog;

public class CustomMessage extends ConstraintLayout {

    private ImageView imageView;
    private MessageObject currentMessageObject;

    public CustomMessage(@NonNull Context context) {
        super(context);

        LayoutParams lp = new ConstraintLayout.LayoutParams(LayoutParams.MATCH_PARENT, AndroidUtilities.dp(200));
        setLayoutParams(lp);

        imageView = new ImageView(context);
//        Glide.with(this).load("https://api.cryptogram.xyz/TonOfMemesBot/resources/0e658520-3199-453d-a5f6-168217025525/TENISh.png").into(imageView);

        ConstraintLayout.LayoutParams imageViewLP = new ConstraintLayout.LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.MATCH_PARENT);
        imageViewLP.dimensionRatio="H,269:400";
        imageViewLP.setMargins(AndroidUtilities.dp(20), AndroidUtilities.dp(10), AndroidUtilities.dp(20), AndroidUtilities.dp(10));
        imageViewLP.topToTop = LayoutParams.PARENT_ID;
        imageViewLP.bottomToBottom = LayoutParams.PARENT_ID;
        imageViewLP.rightToRight = LayoutParams.PARENT_ID;
        imageViewLP.leftToLeft = LayoutParams.PARENT_ID;
//        imageViewLP.horizontalBias=1;

        imageView.setLayoutParams(imageViewLP);
        addView(imageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(200), MeasureSpec.EXACTLY));
    }

    public MessageObject getMessageObject() {
        return currentMessageObject;
    }

    public void setMessageObject(MessageObject messageObject) {
         currentMessageObject = messageObject;
         setImage();
    }

    private void setImage() {

        try {
            String url = currentMessageObject.messageText.toString();
            Uri uri = Uri.parse(url);
            String ids = uri.getQueryParameter("startapp");
            String id = null;

            if(ids != null) {
                String[] splitIds = ids.split("_c");

                if (splitIds != null && splitIds.length > 1) {
                    id = splitIds[1];

                    String[] idParts = id.split("\\s+");
                    if(idParts != null && splitIds.length>1)
                        id =idParts[0];
                }
            }

            CustomBotMenuController.getInstance().getCoinIcon(id, new CoinCallback() {
                @Override
                public void success(String coinUrl) {
                    Glide.with(imageView.getContext()).load(coinUrl).into(imageView);
                }

                @Override
                public void error(String error) {
                    Glide.with(imageView.getContext()).clear(imageView);

                    AlertDialog dialog =
                            new AlertDialog.Builder(imageView.getContext())
                                    .setTitle("setImage")
                                    .setMessage(error)
                                    .setPositiveButton("Cool", null)
                                    .setNegativeButton("Poorly", null)
                                    .create();
                    dialog.show();
                }
            });

        } catch (Exception ex) {
            AlertDialog dialog =
                    new AlertDialog.Builder(imageView.getContext())
                            .setTitle("setImage")
                            .setMessage(ex.getMessage())
                            .setPositiveButton("Cool", null)
                            .setNegativeButton("Poorly", null)
                            .create();
            dialog.show();
        }
    }

    public void setIsMine(boolean isMine){
        ConstraintLayout.LayoutParams imageViewLP = (ConstraintLayout.LayoutParams)imageView.getLayoutParams();
        imageViewLP.horizontalBias= isMine ? 1 : 0;
        imageView.setLayoutParams(imageViewLP);
    }

    public void openBotFromMessage(){
        String link =currentMessageObject.messageText.toString();

        String[] linkParts = link.split("\\s+");
        if(linkParts != null && linkParts.length>1)
            link =linkParts[0];

        Browser.openUrl(getContext(), Uri.parse(link), true, true, false, null, null);
    }
}
