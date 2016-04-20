package com.cqupt.jobclient.utils;

import android.text.format.Time;
import android.util.Log;

import com.cqupt.jobclient.acticity.app;
import com.cqupt.jobclient.model.DetailsArticleCQUPT;
import com.cqupt.jobclient.model.DetailsArticleHK;
import com.cqupt.jobclient.model.DetailsArticleNY;
import com.cqupt.jobclient.model.DetailsArticleSC;
import com.cqupt.jobclient.model.DetailsArticleXD;
import com.cqupt.jobclient.model.DocumentItem;
import com.cqupt.jobclient.model.MessageItemCQUPT;
import com.cqupt.jobclient.model.MessageItemHK;
import com.cqupt.jobclient.model.MessageItemNY;
import com.cqupt.jobclient.model.MessageItemSC;
import com.cqupt.jobclient.model.MessageItemXD;
import com.cqupt.jobclient.model.MessageItemXDFirst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/19.
 */
public class GetData {
//    unicode转化为中文
    public static String decodeUnicode(String theString) {

        char aChar;

        int len = theString.length();

        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {

            aChar = theString.charAt(x++);

            if (aChar == '\\') {

                aChar = theString.charAt(x++);

                if (aChar == 'u') {

                    // Read the xxxx

                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = theString.charAt(x++);

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';

                    else if (aChar == 'n')

                        aChar = '\n';

                    else if (aChar == 'f')

                        aChar = '\f';

                    outBuffer.append(aChar);

                }

            } else

                outBuffer.append(aChar);

        }

        return outBuffer.toString();

    }

    public static  ArrayList<MessageItemCQUPT> getMessageItemCQUT(String date) {
        String CQUPTURL = "";
        ArrayList<MessageItemCQUPT> messageItemCQUPTArrayList = null;
        MessageItemCQUPT messageItemCQUPT = null;
        CQUPTURL = "http://job.cqupt.edu.cn/main/getClnd/"+date;
//        CQUPTURL = "http://job.cqupt.edu.cn/main/jobClnd/2016-04";
        try {
            Document document = Jsoup.connect(CQUPTURL).timeout(5000).get();
            try {
                String cqupt = decodeUnicode(document.getElementsByTag("body").get(0).text());
                JSONObject jsonObject_all = new JSONObject(cqupt);
                messageItemCQUPTArrayList = new ArrayList<>();
//                JSON不规范数据格式解析范例
                for (int i = 1; i < 8; i++) {
                    JSONObject jsonObject_date = null;
                    jsonObject_date = jsonObject_all.getJSONObject(i + "");
                    date = jsonObject_date.getString("date");
                    String length = jsonObject_date.getString("length");
                    if (length != "0") {
                        JSONObject jsonObject_details = null;
                        for (int j = 0; j < Integer.parseInt(length); j++) {
                            jsonObject_details = jsonObject_date.getJSONObject(j + "");
                            messageItemCQUPT = new MessageItemCQUPT();
                            String id = jsonObject_details.getString("id");
                            String title = jsonObject_details.getString("title");

                            String scan = "浏览次数：" + jsonObject_details.getString("scan");
                            String target = jsonObject_details.getString("target");
                            String classroom = jsonObject_details.getString("classroom");
                            String startTime = jsonObject_details.getString("startTime");
                            String num = "";
                            if(Integer.parseInt(jsonObject_details.getString("num"))==0){
                                num = "需求人数：待定";
                            }else{
                                num = "需求人数：" + jsonObject_details.getString("num");
                            }
                            messageItemCQUPT.setMessageTitle(title);
                            messageItemCQUPT.setMessageTime(startTime);
                            messageItemCQUPT.setMessagePlace(classroom);
                            messageItemCQUPT.setMessageUrl("http://job.cqupt.edu.cn/main/rec/" + id + "/");
                            messageItemCQUPT.setMessageNeedNum(num);
                            messageItemCQUPT.setMessageViewTime(scan);
                            switch (Integer.parseInt(target)){
                                case 1:
                                    messageItemCQUPT.setMessageNeedType("招聘类别：职员");
                                    break;
                                case 2:
                                    messageItemCQUPT.setMessageNeedType("招聘类别：实习生");
                                    break;
                                case 3:
                                    messageItemCQUPT.setMessageNeedType("招聘类别：职员/实习生");
                                    break;
                            }

                            messageItemCQUPTArrayList.add(messageItemCQUPT);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageItemCQUPTArrayList;
    }
    public static ArrayList<MessageItemSC> getMessageItemSC(int page) {
        String SCURL = "";
        ArrayList<MessageItemSC> messageItemSCList = null;
        SCURL = "http://jy.scu.edu.cn/jiuye/news.php?sid=&start=" + page * 22 + "&type_id=4";
        if(!NetWorkUtil.isNetWorkConnected(app.getContext())){
            return null;
        }
        try {
            Document doc = Jsoup.connect(SCURL).timeout(5000).get();
//            Log.e("初步抓取网页源码", doc.toString());
            if(doc!=null) {
                Elements elements_messageSC = doc.select("td[valign=\"top\"]");
//                Log.e("抓取内容成功",elements_messageSC.get(3).toString());
                messageItemSCList = new ArrayList<>();
                Elements elements_messageSCList = elements_messageSC.get(3).getElementsByClass("news");
                MessageItemSC messageItemSC = null;
                for(Element element:elements_messageSCList) {
                    messageItemSC = new MessageItemSC();
                    messageItemSC.setMessageTitle(element.attr("title"));
                    messageItemSC.setMessageUrl("http://jy.scu.edu.cn/jiuye/" + element.attr("href"));
//                    Log.e("messageItemSC", messageItemSC.toString());
                    messageItemSCList.add(messageItemSC);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageItemSCList;
    }

    public static ArrayList<MessageItemNY> getMessageItemNY(int page) {
        String NYURL = "http://njupt.91job.gov.cn/teachin/index?page=" + (page+1);
//        Log.e("抓取网址",NYURL);
        ArrayList<MessageItemNY> messageItemNYList = null;
        MessageItemNY messageItemNY = null;
        Document document = null;
        if (!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }

        try {
            document = Jsoup.connect(NYURL).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(document==null) {
                return null;
            }
//            Elements elements_NY = document.getElementsByAttributeValue("class", "span1");
//            Elements elements_NY = document.getElementsByAttributeValueContaining("class","teachinList");
//            Elements elements_NY = document.getElementsByClass("span1");
        //此处电脑网页端访问与手机客户端访问源码不同
        Elements elements_NY = document.getElementsByClass("list-group-item");
        messageItemNYList = new ArrayList<>();
            for(Element element_NY:elements_NY) {
                messageItemNY = new MessageItemNY();
                String title = element_NY.getElementsByTag("h2").get(0).text();
                messageItemNY.setMessageTitle(title);
                String url = "http://njupt.91job.gov.cn"+element_NY.getElementsByTag("a").get(0).attr("href");
                messageItemNY.setMessageUrl(url);
                String place = element_NY.getElementsByClass("text-default").get(0).text();
                messageItemNY.setMessagePlace(place);
                String time = element_NY.getElementsByClass("text-default").get(1).text();
                messageItemNY.setMessageTime(time);
                messageItemNYList.add(messageItemNY);
            }

        return messageItemNYList;
    }

    public static ArrayList<MessageItemXD> getMessageItemXD(int pageLoad) {
        String XDURL = "http://job.xidian.edu.cn/html/zpxx/jobs/list_3_"+(pageLoad+1)+".html";
        ArrayList<MessageItemXD> messageItemXDList = null;
        MessageItemXD messageItemXD = null;
        Document document = null;
        if (!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }
        try {
            document = Jsoup.connect(XDURL).timeout(5000).get();
            if(document==null) {
                return null;
            }


            Element elements_XD_arcList = document.getElementsByClass("arcList").get(0);
            Elements elements_XD = elements_XD_arcList.getElementsByTag("li");
            messageItemXDList = new ArrayList<>();
            //添加南部校区招聘会
            messageItemXD = new MessageItemXD();
            messageItemXD.setMessageTitle("南校区招聘会安排");
            Time time=new Time();
            time.setToNow();
            String curTime = "发布时间："+time.year + "年" + (time.month+1) + "月" + time.monthDay + "日";
            messageItemXD.setMessagePostTime(curTime);
            messageItemXD.setMessageUrl("http://job.xidian.edu.cn/html/zpxx/nxqzph/");
            messageItemXDList.add(messageItemXD);
            for(Element element_XD:elements_XD) {
                messageItemXD = new MessageItemXD();
                String postTime = "发布时间："+element_XD.getElementsByTag("span").get(0).text();
                String title = element_XD.getElementsByTag("a").get(0).text();
                String url = "http://job.xidian.edu.cn"+element_XD.getElementsByTag("a").get(0).attr("href");
//                Log.e("postTime",postTime);
//                Log.e("title",title);
//                Log.e("url",url);
                messageItemXD.setMessagePostTime(postTime);
                messageItemXD.setMessageUrl(url);
                messageItemXD.setMessageTitle(title);
                messageItemXDList.add(messageItemXD);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageItemXDList;
    };

    public static ArrayList<MessageItemHK> getMessageItemHK(int pageLoad) {
        ArrayList<MessageItemHK> messageItemHKList = null;
        MessageItemHK messageItemHK = null;
        String HKURL = "http://job.hust.edu.cn/show/recruitnews/jobnewslist.htm?page="+(pageLoad+1);
        Document document = null;
        if(!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }
        try {
            document = Jsoup.connect(HKURL).timeout(5000).get();
            if(document==null) {
                return null;
            }
            Elements elements_HK = document.getElementsByClass("even");
            if(elements_HK.size()<=0) {
                return null;
            }
            messageItemHKList = new ArrayList<>();
            for(Element element_HK:elements_HK) {
                messageItemHK = new MessageItemHK();
                String url = "http://job.hust.edu.cn/show"+
                        element_HK.getElementsByTag("a").get(0).attr("href").substring(2);
                String title = element_HK.getElementsByTag("a").get(0).text();
                String postTime ="发布时间："+ element_HK.getElementsByTag("td").get(1).text();
                String viewNum = "浏览次数："+element_HK.getElementsByTag("td").get(2).text();
//                Log.e("抓取数据",pageLoad+url+title+postTime+viewNum);
                messageItemHK.setMessageTitle(title);
                messageItemHK.setMessageUrl(url);
                messageItemHK.setMessagePostTime(postTime);
                messageItemHK.setMessageViewNum(viewNum);
                messageItemHKList.add(messageItemHK);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageItemHKList;
    }

    public static ArrayList<DetailsArticleCQUPT> getDetailsArticleCQUPT(String url) {
        ArrayList<DetailsArticleCQUPT> detailsArticleCQUPTArrayList = null;
        DetailsArticleCQUPT detailsArticleCQUPT = null;
        Document document = null;
        try {
            document = Jsoup.parse(new URL(url),5000);
            if(document==null) {
                return null;
            }
            detailsArticleCQUPTArrayList = new ArrayList<>();
            Element element_title = document.getElementsByClass("h2").get(0);
            String title = element_title.text();
            detailsArticleCQUPT = new DetailsArticleCQUPT();
            detailsArticleCQUPT.setTitle(title);
            detailsArticleCQUPT.setType(0);
            detailsArticleCQUPTArrayList.add(detailsArticleCQUPT);

            Element element = document.getElementsByClass("passage_3").get(0);
            Elements elements_p = element.getElementsByTag("p");
            for(Element element_p:elements_p) {
                detailsArticleCQUPT = new DetailsArticleCQUPT();
                String text = element_p.text();
                detailsArticleCQUPT.setText(text);
                detailsArticleCQUPT.setType(2);
                detailsArticleCQUPTArrayList.add(detailsArticleCQUPT);
            }

            Element element_doc = element.getElementsByTag("div").get(1).getElementsByTag("li").get(0);
            String document_text = "附件："+element_doc.text();
            String document_url = "";
            Elements elements_a = element_doc.getElementsByTag("a");
            if(elements_a.size()>0) {
                Element element_a = elements_a.get(0);
                document_url = "http://job.cqupt.edu.cn"+element_a.attr("href");
            }
            Log.e("详细网址",document_url);
            DocumentItem documentItem = new DocumentItem();
            documentItem.setDocument(document_text);
            documentItem.setDocumentUrl(document_url);
            detailsArticleCQUPT = new DetailsArticleCQUPT();
            detailsArticleCQUPT.setDocument(documentItem);
            detailsArticleCQUPT.setType(3);
            detailsArticleCQUPTArrayList.add(detailsArticleCQUPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return detailsArticleCQUPTArrayList;
    }

    public static ArrayList<DetailsArticleSC> getDetailsArticleSC(String url) {
//        Log.e("启动新的页面开始获取数据",url);
        ArrayList<DetailsArticleSC> detailsArticleSCList = null;
        DetailsArticleSC detailsArticleSC = null;
        Document document = null;
        try {
            document = Jsoup.connect(url).timeout(5000).get();
            if(document ==null) {
                return null;
            }
//            Element element = document.select
//                    ("table[align=\"center\" bgcolor=\"#FFFFFF\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\"]").get(0);
            Element element = document.select("table[border=\"1\"]").get(0);
            detailsArticleSCList = new ArrayList<>();

            Element element_mainTitle = element.select("td[background=\"image/mainnavbar.jpg\"]").get(0);
            detailsArticleSC = new DetailsArticleSC();
            detailsArticleSC.setMainTitle(element_mainTitle.text());
//            Log.e("初步抓取文章题目", element_mainTitle.text());
            detailsArticleSC.setType(0);
            detailsArticleSCList.add(detailsArticleSC);

            Element element_time = element.select("td[bgcolor=\"#E4E4E4\"]").get(0);
            detailsArticleSC = new DetailsArticleSC();
            detailsArticleSC.setTime(element_time.text());
            detailsArticleSC.setType(1);
//            Log.e("初步抓取时间", element_time.text());
            detailsArticleSCList.add(detailsArticleSC);

            Element element_recruit = element.select("font[color=\"red\"]").get(0);
            if(element_recruit.text().length()>0){
                detailsArticleSC = new DetailsArticleSC();
                String recruit = element_recruit.text();
                int timeNum = recruit.indexOf(" ");
                String time = recruit.substring(0, timeNum);
                String place = recruit.substring(timeNum+1);
                String finalRecruit = time + "\n" + place;
                detailsArticleSC.setRecruit(finalRecruit);
                detailsArticleSC.setType(4);
                detailsArticleSCList.add(detailsArticleSC);
            }

            Elements elements_article = element.getElementsByTag("p");
            for(Element element_article:elements_article){
                if(element_article.select("p[style=\"TEXT-ALIGN: center; LINE-HEIGHT: 150%\"]").size()>0) {
                    detailsArticleSC = new DetailsArticleSC();
                    String title = element_article.select
                            ("p[style=\"TEXT-ALIGN: center; LINE-HEIGHT: 150%\"]").get(0).text();
                    detailsArticleSC.setTitle(title);
                    detailsArticleSC.setType(2);
//                    Log.e("抓取标题", title);
                    detailsArticleSCList.add(detailsArticleSC);
                }else {
                    detailsArticleSC = new DetailsArticleSC();
                    String text = "  "+element_article.text();
                    detailsArticleSC.setText(text);
                    detailsArticleSC.setType(3);
//                    Log.e("抓取正文",text);
                    detailsArticleSCList.add(detailsArticleSC);
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailsArticleSCList;
    }

    public static ArrayList<DetailsArticleNY> getDetailsArticleNY(String url) {
        ArrayList<DetailsArticleNY> detailsArticleNYs = null;
        DetailsArticleNY detailsArticleNY = null;
        Document document = null;
        if(!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }
        try {
            document = Jsoup.connect(url).timeout(5000).get();
            if(document==null) {
                return null;
            }
            Log.e("url", url);
            Log.e("document",document.toString());
//            Element element = document.getElementsByClass("container").get(0);
            detailsArticleNYs = new ArrayList<>();
//            Log.e("element", element.toString());
//            title
            Element element_details = document.getElementsByClass("css-header").get(0);
            String title = element_details.getElementsByTag("h3").get(0)
                    .text();
            Log.e("title", title);
            detailsArticleNY = new DetailsArticleNY();
            detailsArticleNY.setType(0);
            detailsArticleNY.setTitle(title);
            detailsArticleNYs.add(detailsArticleNY);

            Elements elements_dt = element_details.getElementsByTag("dt");
            Elements elements_dd = element_details.getElementsByTag("dd");
            if(elements_dd.size()!=elements_dt.size()) {
                return null;
            }
            for(int i =0;i<elements_dt.size();i++){
                String text = elements_dt.get(i).text()+elements_dd.get(i).text();
                Log.e("text", text);
                detailsArticleNY = new DetailsArticleNY();
                detailsArticleNY.setType(1);
                detailsArticleNY.setText(text);
                detailsArticleNYs.add(detailsArticleNY);
            }
            Elements elements_text_p = document.getElementsByClass("css-conbox").get(0).getElementsByTag("p");
            for(Element element_text_p:elements_text_p) {
                String text = element_text_p.text();
                Log.e("text", text);
                detailsArticleNY = new DetailsArticleNY();
                detailsArticleNY.setType(2);
                detailsArticleNY.setText(text);
                detailsArticleNYs.add(detailsArticleNY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailsArticleNYs;
    }

    public static ArrayList<DetailsArticleXD> getDetailsArticleXD(String url) {
        ArrayList<DetailsArticleXD> detailsArticleXDs = null;
        DetailsArticleXD detailsArticleXD = null;
        Document document = null;
        Document document_num = null;
        if(!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }
        try {
            document = Jsoup.connect(url).get();
            Elements element_mains = document.getElementsByClass("main");
            if(element_mains!=null&&element_mains.size()>0) {
                detailsArticleXDs = new ArrayList<>();
                Element element_main = element_mains.get(0);
                Element element_arcTitle = element_main.getElementsByClass("arcTitle").get(0);
                detailsArticleXD = new DetailsArticleXD();
                String title = element_arcTitle.text().substring(0, element_arcTitle.text().length() - 4);
                detailsArticleXD.setTitle(title);
//                Log.e("title", detailsArticleXD.getTitle());
                detailsArticleXD.setType(0);
                detailsArticleXDs.add(detailsArticleXD);

                Element element_arcInfo = element_main.getElementsByClass("arcInfo").get(0);
                detailsArticleXD = new DetailsArticleXD();
                String info = element_arcInfo.text();
                info = info.substring(0,21);
                detailsArticleXD.setTime(info);
//                Log.e("arcInfo", detailsArticleXD.getTime());
                detailsArticleXD.setType(1);
                detailsArticleXDs.add(detailsArticleXD);

                Elements elements_arcContents = element_main.getElementsByClass("arcContent").get(0)
                        .getElementsByTag("p");
                if(elements_arcContents.size()>0){
                    for(Element element_arcContent:elements_arcContents) {
                        detailsArticleXD = new DetailsArticleXD();
//                        Log.e("arcContent", element_arcContent.text());
                        detailsArticleXD.setText(element_arcContent.text());
                        detailsArticleXD.setType(2);
                        detailsArticleXDs.add(detailsArticleXD);
                    }
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailsArticleXDs;
    }
    public static ArrayList<DetailsArticleHK> getDetailsArticleHK(String url) {
        ArrayList<DetailsArticleHK> detailsArticleHKs = null;
        DetailsArticleHK detailsArticleHK = null;
        Document document = null;
        if(!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }
        try {
            Log.e("开始抓取", url);
            document = Jsoup.connect(url).get();
            Element element_detail = document.getElementById("detail");
            detailsArticleHKs = new ArrayList<>();

            Element element_content_title = element_detail.getElementById("content_title");
            detailsArticleHK = new DetailsArticleHK();
            Log.e("title", element_content_title.text());
            detailsArticleHK.setTitle(element_content_title.text());
            detailsArticleHK.setType(0);
            detailsArticleHKs.add(detailsArticleHK);

            Element element_scancount = element_detail.getElementsByClass("scancount").get(0);
            detailsArticleHK = new DetailsArticleHK();
            detailsArticleHK.setTime(element_scancount.text());
            detailsArticleHK.setType(1);
            Log.e("scancount", detailsArticleHK.getTime());
            detailsArticleHKs.add(detailsArticleHK);

            Elements elements_content = element_detail.getElementsByTag("p");
            if (elements_content.size() > 0) {
                for (Element element_content : elements_content) {
                    detailsArticleHK = new DetailsArticleHK();
                    detailsArticleHK.setText(element_content.text());
                    detailsArticleHK.setType(2);
                    Log.e("p", detailsArticleHK.getText());
                    detailsArticleHKs.add(detailsArticleHK);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailsArticleHKs;
    }

    public static ArrayList<MessageItemXDFirst> getMessageItemXDFirst() {
        String url = "http://job.xidian.edu.cn/html/zpxx/nxqzph/";
        ArrayList<MessageItemXDFirst> messageItemXDFirsts = null;
        MessageItemXDFirst messageItemXDFirst = null;
        Document document = null;
        if(!NetWorkUtil.isNetWorkConnected(app.getContext())) {
            return null;
        }
        try {
            document = Jsoup.connect(url).get();
            Elements element_zphTables = document.getElementsByClass("zphTable");
            if(element_zphTables.size()>0) {
                Element element_zphTable = element_zphTables.get(0);
                Elements elements_trs = element_zphTable.getElementsByTag("tr");
                messageItemXDFirsts = new ArrayList<>();
                int index;
                int num = elements_trs.size();
                Elements element_tds = null;
                Element element_td = null;
                for(index = 1;index<num;index++) {
                    element_tds = elements_trs.get(index).getElementsByTag("td");
                    messageItemXDFirst = new MessageItemXDFirst();
                    String title = element_tds.get(3).text();
                    String time = "时间：20" + element_tds.get(0).text() + element_tds.get(1).text();
                    String place = "地点："+element_tds.get(2).text();
                    String messageUrl = "http://job.xidian.edu.cn"+element_tds.get(3).getElementsByTag("a").get(0).attr("href");
                    Log.e("message", title + time + place + messageUrl);
                    messageItemXDFirst.setMessageTitle(title);
                    messageItemXDFirst.setMessageUrl(messageUrl);
                    messageItemXDFirst.setMessagePlace(place);
                    messageItemXDFirst.setMessageTime(time);
                    messageItemXDFirsts.add(messageItemXDFirst);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messageItemXDFirsts;
    }
}
