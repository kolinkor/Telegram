package org.telegram.betTest;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.browser.Browser;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.WebAppDisclaimerAlert;
import org.telegram.ui.bots.BotWebViewAttachedSheet;
import org.telegram.ui.bots.BotWebViewSheet;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomMessage extends ConstraintLayout {

    private ImageView imageView;
    private MessageObject currentMessageObject;
    private String coinId;
    private String link;

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

            coinId = id;

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

    public void openBotFromMessage() {
        String link = currentMessageObject.messageText.toString();

        String[] linkParts = link.split("\\s+");
        if (linkParts != null && linkParts.length > 1)
            link = linkParts[0];

        if(link.isEmpty())
        {
            //TODO Open bot
            return;
        }

        this.link = link;
        GetBotInfo();
//        Browser.openUrl(getContext(), Uri.parse(link), true, true, false, null, null);
    }

    private void GetBotInfo(){
        String username="TonOfMemesBot";
        MessagesController.getInstance(currentMessageObject.currentAccount).getUserNameResolver().resolve(username, (peerId) -> {
            GetBotInfo(peerId);
        });
    }

    private void GetBotInfo(Long peerId){

        TLRPC.TL_messages_getBotApp getBotApp = new TLRPC.TL_messages_getBotApp();
        TLRPC.TL_inputBotAppShortName app = new TLRPC.TL_inputBotAppShortName();

        TLRPC.User user = MessagesController.getInstance(currentMessageObject.currentAccount).getUser(peerId);
        app.bot_id = MessagesController.getInstance(currentMessageObject.currentAccount).getInputUser(user);
        app.short_name = "app";
//        app.short_name = botAppMaybe;
        getBotApp.app = app;

        ConnectionsManager.getInstance(currentMessageObject.currentAccount).sendRequest(getBotApp, (response1, error1) -> {
            if (error1 != null) {
//                AndroidUtilities.runOnUIThread(() -> runLinkRequest(intentAccount, username, group, sticker, emoji, botUser, botChat, botChannel, botChatAdminParams, message, contactToken, folderSlug, text, hasUrl, messageId, channelId, threadId, commentId, game, auth, lang, unsupportedUrl, code, loginToken, wallPaper, inputInvoiceSlug, theme, voicechat, videochat, livestream, state, videoTimestamp, setAsAttachBot, attachMenuBotToOpen, attachMenuBotChoose, null, null, progress, forceNotInternalForApps, storyId, isBoost, chatLinkSlug, botCompact, openedTelegram));
            } else {
                TLRPC.TL_messages_botApp botApp = (TLRPC.TL_messages_botApp) response1;
                AndroidUtilities.runOnUIThread(() -> {
                    GetBotInfo(botApp);
                });
            }
        });
    }

    private void GetBotInfo(TLRPC.TL_messages_botApp botAppInfo){

//        BotWebViewAttachedSheet.WebViewRequestProps props =
//         BotWebViewAttachedSheet.WebViewRequestProps.of(intentAccount, user.id, user.id, null, null, BotWebViewSheet.TYPE_WEB_VIEW_BOT_APP, 0,
//         false, botApp.app, allowWrite.get(), botAppStartParam, user, 0, botCompact);


        TLRPC.TL_messages_requestAppWebView req = new TLRPC.TL_messages_requestAppWebView();
        TLRPC.TL_inputBotAppID botApp = new TLRPC.TL_inputBotAppID();
        botApp.id = botAppInfo.app.id;
        botApp.access_hash =botAppInfo.app.access_hash;

        req.app = botApp;
        req.write_allowed = false;
//        req.write_allowed = botAppInfo.allowWrite;

        req.platform = "android";
        req.peer = MessagesController.getInputPeer(currentMessageObject.getPeerObject());
//                fragment instanceof ChatActivity ? ((ChatActivity) fragment).getCurrentUser() != null ? MessagesController.getInputPeer(((ChatActivity) fragment).getCurrentUser()) : MessagesController.getInputPeer(((ChatActivity) fragment).getCurrentChat())
//                : MessagesController.getInputPeer(props.botUser);
        //req.compact = props.compact;


//        if (!TextUtils.isEmpty(props.startParam)) {
//            req.start_param = props.startParam;
//            req.flags |= 2;
//        }

//        if (themeParams != null) {
//            req.theme_params = new TLRPC.TL_dataJSON();
//            req.theme_params.data = themeParams.toString();
//            req.flags |= 4;
//        }

        ConnectionsManager.getInstance(currentMessageObject.currentAccount).sendRequest(req, (response2, error2) -> AndroidUtilities.runOnUIThread(() -> {
            if (error2 != null) {
            }
//            } else if (requestProps != null) {
//                requestProps.applyResponse(response2);
//                loadFromResponse(false);
//            }
            else if(response2 != null){
                handleResponse(response2);
            }
        }), ConnectionsManager.RequestFlagInvokeAfter | ConnectionsManager.RequestFlagFailOnServerErrors);
    }

    private void handleResponse(TLObject response) {
        if (response == null) return;

        String url = null;
        if (response instanceof TLRPC.TL_webViewResultUrl) {
            TLRPC.TL_webViewResultUrl resultUrl = (TLRPC.TL_webViewResultUrl) response;
            long queryId = resultUrl.query_id;
            url = resultUrl.url;
        } else if (response instanceof TLRPC.TL_appWebViewResultUrl) { // deprecated
            TLRPC.TL_appWebViewResultUrl result = (TLRPC.TL_appWebViewResultUrl) response;
            url = result.url;
        } else if (response instanceof TLRPC.TL_simpleWebViewResultUrl) { // deprecated
            TLRPC.TL_simpleWebViewResultUrl resultUrl = (TLRPC.TL_simpleWebViewResultUrl) response;
            url = resultUrl.url;
        }

        if (url.isEmpty())
            return;

        String webAppData = UrlParser.GetTgWebAppData(url);
        if(webAppData.isEmpty())
            return;

        BuyCoinData buyCoinData = new BuyCoinData();
        buyCoinData.amount = "0.0001";

        CustomBotMenuController.getInstance().BuyCoin(coinId, buyCoinData, webAppData, new ByuCoinCallback() {
            @Override
            public void success(String coinAmount) {
                AlertDialog dialog =
                        new AlertDialog.Builder(imageView.getContext())
                                .setTitle("Congratulations!")
                                .setMessage("You have successfully bought "+coinAmount+" coins")
                                .setPositiveButton("OK", null)
                                .create();

                dialog.show();
            }

            @Override
            public void error(String error) {
                AlertDialog dialog =
                        new AlertDialog.Builder(imageView.getContext())
                                .setTitle(error)
                                .setMessage("Do you want to top up your account?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Browser.openUrl(getContext(), Uri.parse(link), true, true, false, null, null);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .create();
                dialog.show();
            }
        });
    }
}
