package kanzhihu.android.activities.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import kanzhihu.android.App;
import kanzhihu.android.AppConstant;
import kanzhihu.android.R;
import kanzhihu.android.activities.adapter.base.CursorRecyclerViewAdapter;
import kanzhihu.android.models.Article;

/**
 * Created by Jiahui.wen on 2014/11/13.
 */
public class SearchAdapter extends CursorRecyclerViewAdapter {

    private String mCurFilter;
    private String mHightLight;

    public SearchAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        Article article = Article.fromCursor(cursor);
        ArticlesAdapter.ArticleHolder holder = (ArticlesAdapter.ArticleHolder) viewHolder;
        setHightLight(holder.mTitle, article.title);
        setHightLight(holder.mContent, article.summary);
        holder.mAuthor.setText(article.writer);
        holder.mAgree.setText(String.valueOf(article.agreeCount));

        Picasso.with(App.getAppContext())
            .load(String.format(AppConstant.IMAGE_LINK, article.imageLink))
            .into(holder.mAvatar);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_article, viewGroup, false);
        return new ArticlesAdapter.ArticleHolder(v);
    }

    public void setCurFilter(String newText) {
        mCurFilter = newText;
        if (TextUtils.isEmpty(mCurFilter)) {
            mHightLight = "";
        } else {
            mHightLight = "<font color='#009688'>" + mCurFilter + "</font>";
        }
    }

    private void setHightLight(TextView view, String text) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(mCurFilter)) {
            view.setText(text);
        } else {
            //Todo replacement ignore CASE_INSENSITIVE
            String summary = text.replaceAll(mCurFilter, mHightLight);
            Spanned span = Html.fromHtml(summary);
            view.setText(span);
        }
    }
}
