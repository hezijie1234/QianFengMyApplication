package day23downappbyservice.day32butterknifetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hezijie on 2017/2/4.
 */
public class ListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;

    public ListViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            convertView = inflater.inflate(R.layout.fragment_one_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText("Item-----"+position);
        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.fragment_one_item_tv)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
