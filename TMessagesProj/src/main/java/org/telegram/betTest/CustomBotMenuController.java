package org.telegram.betTest;

import com.google.gson.Gson;

import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomBotMenuController {

    private HashMap<String, String> _imageCache = new HashMap<>();
    private static String botName = "TonOfMemesBot";
    private static String baseUrl = "https://api.cryptogram.xyz";
    private static String getCoinIconUrl = "https://api.cryptogram.xyz/api/v1/bots/%s/resources/%s";
    private static String defaultPictureAddress = "https://www.eclosio.ong/wp-content/uploads/2018/08/default.png";
    private static String BuyCoinUrl = "https://ton-of-memes-api.alcornlabs.ee/api/trade/%s/buy";
    private static final Gson gson = new Gson();

    private static CustomBotMenuController _localInstance;

    public static CustomBotMenuController getInstance() {

        if (_localInstance == null) {
            _localInstance = new CustomBotMenuController();
        }

        return _localInstance;
    }

    public ArrayList<TLRPC.TL_attachMenuBot> GetCustomBots() {
        ArrayList<TLRPC.TL_attachMenuBot> list = new ArrayList<TLRPC.TL_attachMenuBot>();

        TLRPC.TL_attachMenuBot tonOfMemesBot = new TLRPC.TL_attachMenuBot();
        tonOfMemesBot.short_name = "TonOfMemes";
        tonOfMemesBot.bot_id = 7296734780l;
        //tonOfMemesBot.url="https://telegram-app-5541e.web.app/";
        tonOfMemesBot.url = "https://t.me/TonOfMemesBot/app";
        //tonOfMemesBot.url="https://telegram-app-5541e.web.app/#tgWebAppData=user%3D%257B%2522id%2522%253A158980516%252C%2522first_name%2522%253A%2522Den%2522%252C%2522last_name%2522%253A%2522Samiilenko%2522%252C%2522username%2522%253A%2522den_samiilenko%2522%252C%2522language_code%2522%253A%2522en%2522%252C%2522allows_write_to_pm%2522%253Atrue%257D%26chat_instance%3D231722781122685378%26chat_type%3Dprivate%26auth_date%3D1722583303%26hash%3D9ba28b454177711d1826797f94be33fe1ed61253d962cd1e4dd051f1de937dce&tgWebAppVersion=7.6&tgWebAppPlatform=android&tgWebAppThemeParams=%7B%22bg_color%22%3A%22%23ffffff%22%2C%22section_bg_color%22%3A%22%23ffffff%22%2C%22secondary_bg_color%22%3A%22%23f0f0f0%22%2C%22text_color%22%3A%22%23222222%22%2C%22hint_color%22%3A%22%23a8a8a8%22%2C%22link_color%22%3A%22%232678b6%22%2C%22button_color%22%3A%22%2350a8eb%22%2C%22button_text_color%22%3A%22%23ffffff%22%2C%22header_bg_color%22%3A%22%23527da3%22%2C%22accent_text_color%22%3A%22%231c93e3%22%2C%22section_header_text_color%22%3A%22%233a95d5%22%2C%22subtitle_text_color%22%3A%22%2382868a%22%2C%22destructive_text_color%22%3A%22%23cc2929%22%2C%22section_separator_color%22%3A%22%23d9d9d9%22%7D";

        ArrayList<TLRPC.TL_attachMenuBotIcon> icons = new ArrayList<>();
        TLRPC.TL_attachMenuBotIcon icon = new TLRPC.TL_attachMenuBotIcon();
        icon.name = "android_animated";

        ArrayList<TLRPC.TL_attachMenuBotIconColor> colors = new ArrayList<TLRPC.TL_attachMenuBotIconColor>() {
            {
                add(new TLRPC.TL_attachMenuBotIconColor() {//iconBackgroundColor
                    {
                        name = "light_icon";
                        color = 0xFF0000;//red
                    }
                });
                add(new TLRPC.TL_attachMenuBotIconColor() {//textColor
                    {
                        name = "light_text";
                        color = 0x0000FF;//blue
                    }
                });
                add(new TLRPC.TL_attachMenuBotIconColor() {//iconBackgroundColor
                    {
                        name = "dark_icon";
                        color = 0xFFFF00;//yellow
                    }
                });
                add(new TLRPC.TL_attachMenuBotIconColor() {//textColor
                    {
                        name = "dark_text";
                        color = 0x00FF00;//lime
                    }
                });
            }
        };


        icon.colors = colors;

        TLRPC.TL_document icon1 = new TLRPC.TL_document();
        icon1.id = 5370994588062325324l;
//        icon1.id=5370994588062325l;
        icon1.access_hash = -4885128536569029477l;
        icon1.date = 1649511919;
        icon1.dc_id = 2;
        icon1.file_name_fixed = "AnimatedSticker.tgs";
//        icon1.file_reference = new byte[] {0, 102, -93, -94, -28, -20, 30, 7, 23, 44, -40, 23, 62, -93, -106, 85, 77, -44, -114, -61, -78};
        icon1.mime_type = "application/x-tgsticker";
        icon1.thumbs = new ArrayList<>();
//        icon1.thumbs.add(new TLRPC.PhotoSize() {{
//            bytes = new byte[]{25, 8,-84, 4,-36,88,-128,111,-128,71,0,-128,91,-128,74,8,-119,76,7,73,76,76,74,92,74,108,-127,101,-114,116,-76,114,-90,-127,69,-120,8,-89,-121,7,-100,72,67,125,-108,71,5,-100,82,-105,-85,-76,-101,-118,70,-123,84,
//                    -114,90,-118,71,-108,8,69,-107,9,-125,-105,-112,-104,-119,0,75,-119,6,108,-120,77,0,-127,81,8,-127,-13,-52,25,5,-98,8,-36,107, -128,72,6,-128,76,9,-128,85, -128,114,-123,71, 0,66,95,76,92,126,
//                    -122,70,7,-104,68,-75,-128,-121, 7,-128,-69,-128,-117,7,-128,-111,6,-128,-99,-128,-116,7,74,-114,8,-124,-106,-112,-108,-74,71,-65,97,-118,79,9,-126,84,1,-126,-13,-52,25,6,-108,5,-36,126,69,76,
//                    5,-122,82,6, -128,109,68,75,71,2,-123,72,8,-74,117,-113,0,116,-106,0,112,-68,-125,-114,6,-108,-112,6,-120,7,-125,-117,-121,-96,67,-87,83,-109,80, 9,-120,84,2,-120};
//        }
//        });
//        icon1.thumbs.add(new TLRPC.PhotoSize() {{
//            bytes = new byte[]{25, 8,-84, 4,-36,88,-128,111,-128,71,0,-128,91,-128,74,8,-119,76,7,73,76,76,74,92,74,108,-127,101,-114,116,-76,114,-90,-127,69,-120,8,-89,-121,7,-100,72,67,125,-108,71,5,-100,82,-105,-85,-76,-101,-118,70,-123,84,
//                    -114,90,-118,71,-108,8,69,-107,9,-125,-105,-112,-104,-119,0,75,-119,6,108,-120,77,0,-127,81,8,-127,-13,-52,25,5,-98,8,-36,107, -128,72,6,-128,76,9,-128,85, -128,114,-123,71, 0,66,95,76,92,126,
//                    -122,70,7,-104,68,-75,-128,-121, 7,-128,-69,-128,-117,7,-128,-111,6,-128,-99,-128,-116,7,74,-114,8,-124,-106,-112,-108,-74,71,-65,97,-118,79,9,-126,84,1,-126,-13,-52,25,6,-108,5,-36,126,69,76,
//                    5,-122,82,6, -128,109,68,75,71,2,-123,72,8,-74,117,-113,0,116,-106,0,112,-68,-125,-114,6,-108,-112,6,-120,7,-125,-117,-121,-96,67,-87,83,-109,80, 9,-120,84,2,-120};
//        }
//        });

        icon.icon = icon1;

        icons.add(icon);
        tonOfMemesBot.icons = icons;

        list.add(tonOfMemesBot);
        return list;
    }

    public void getCoinIcon(String id, CoinCallback callback) {
        try {

            if (id == null) {
                callback.success(defaultPictureAddress);
                return;
            }

            String iconUrl = _imageCache.get(id);
            if (iconUrl != null) {
                callback.success(iconUrl);
                return;
            }

            String getUrl = String.format(getCoinIconUrl, botName, id);

            SimpleHttpClient.sendGet(getUrl, new HttpCallback() {
                @Override
                public void onSuccess(String result) {
//                callback.error(result);

                    try {
                        CoinResponse coinResponse = gson.fromJson(result, CoinResponse.class);
                        callback.success(baseUrl + coinResponse.share.url);
                    } catch (Exception ex) {
                        //callback.error(ex.getMessage());
                        callback.success(defaultPictureAddress);
                    }
                }

                @Override
                public void onError(Exception ex) {
                    callback.success(defaultPictureAddress);
                }
            });

        } catch (Exception ex) {
            callback.error(ex.getMessage());
        }
    }

    public void BuyCoin(String coinId, BuyCoinData buyCoinData, String tma, ByuCoinCallback byuCoinCallback) {
        try {
            String getUrl = String.format(BuyCoinUrl, coinId);

            SimpleHttpClient.sendPost(getUrl, buyCoinData, tma, new HttpCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        BuyCoinResponse coinResponse = gson.fromJson(result, BuyCoinResponse.class);
                        byuCoinCallback.success(coinResponse.tradeResult.coinAmount);
                    } catch (Exception ex) {
                        byuCoinCallback.error(ex.getMessage());
                    }
                }

                @Override
                public void onError(Exception ex) {
                    BuyCoinErrorResponse buyCoinErrorResponse = gson.fromJson(ex.getMessage(), BuyCoinErrorResponse.class);
                    byuCoinCallback.error(buyCoinErrorResponse.error);
                }
            });

        } catch (Exception ex) {
            byuCoinCallback.error(ex.getMessage());
        }
    }
}
