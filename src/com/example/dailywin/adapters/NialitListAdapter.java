package com.example.dailywin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dailywin.R;
import com.example.dailywin.model.NailIt;

import java.util.List;

/**
 * Created by Uros on 3.3.14..
 */
public class NialitListAdapter extends ArrayAdapter<NailIt> {
    int resource;
    String response;
    Context context;

    public NialitListAdapter(Context context, int resource, List<NailIt> nailitses){
        super(context, resource, nailitses);
        this.resource = resource;
        this.context = context;  //trololo?
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout nailitListView;
        //Get the current object
        NailIt nailed = getItem(position);

        //Inflate the view
        if(convertView==null)
        {
            nailitListView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, nailitListView, true);
        }
        else
        {
            nailitListView = (LinearLayout) convertView;
        }
        //Get the text boxes from the listitem.xml file
        ImageView icon = (ImageView) nailitListView.findViewById(R.id.list_image);
        TextView labela = (TextView) nailitListView.findViewById(R.id.listItemLabel);

        //Assign the appropriate data from our alert object above
        boolean checked = nailed.isChecked();
        if(checked){
            icon.setImageResource(R.drawable.not_nailed_icon);
        }
        else{
            icon.setImageResource(R.drawable.nailed_icon);
        }

        labela.setText(nailed.getName());

        return nailitListView;
    }

}
