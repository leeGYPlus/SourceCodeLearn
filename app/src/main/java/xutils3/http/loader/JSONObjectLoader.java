package xutils3.http.loader;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.InputStream;

import xutils3.cache.DiskCacheEntity;
import xutils3.common.util.IOUtil;
import xutils3.http.RequestParams;
import xutils3.http.request.UriRequest;

/**
 * Author: wyouflf
 * Time: 2014/06/16
 */
/*package*/ class JSONObjectLoader extends Loader<JSONObject> {

    private String charset = "UTF-8";
    private String resultStr = null;

    @Override
    public Loader<JSONObject> newInstance() {
        return new JSONObjectLoader();
    }

    @Override
    public void setParams(final RequestParams params) {
        if (params != null) {
            String charset = params.getCharset();
            if (!TextUtils.isEmpty(charset)) {
                this.charset = charset;
            }
        }
    }

    @Override
    public JSONObject load(final InputStream in) throws Throwable {
        resultStr = IOUtil.readStr(in, charset);
        return new JSONObject(resultStr);
    }

    @Override
    public JSONObject load(final UriRequest request) throws Throwable {
        request.sendRequest();
        return this.load(request.getInputStream());
    }

    @Override
    public JSONObject loadFromCache(final DiskCacheEntity cacheEntity) throws Throwable {
        if (cacheEntity != null) {
            String text = cacheEntity.getTextContent();
            if (!TextUtils.isEmpty(text)) {
                return new JSONObject(text);
            }
        }

        return null;
    }

    @Override
    public void save2Cache(UriRequest request) {
        saveStringCache(request, resultStr);
    }
}
