package ru.simpls.swiperow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nikulin on 15.09.2014.
 */
public class TemplatesListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<String>> mGroups;
    private MainActivity mContext;
    private String[] groupNames = {"Payment templates"};
    private int[] groupIcons = {R.drawable.templates_icon};
    public TemplatesListAdapter(MainActivity context, ArrayList<ArrayList<String>> groups){
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bankproducts_group_view, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.bankproduct_group_name);
        textGroup.setText(groupNames[groupPosition]);
        ImageView iconGroup = (ImageView) convertView.findViewById(R.id.bankproduct_group_icon);
        iconGroup.setImageDrawable(mContext.getResources().getDrawable(groupIcons[groupPosition]));

        return convertView;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.template_list_item, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.templateName);
        textChild.setText(mGroups.get(groupPosition).get(childPosition));
        final SwipedHorizontalScrollView templateSample = (SwipedHorizontalScrollView) convertView.findViewById(R.id.templateSample);
        final View templateMainLayout = convertView.findViewById(R.id.templateLayoutMainPart);
        templateSample.setTemplateMainLayout(templateMainLayout);
        templateSample.post(
                new Runnable() {
                    public void run() {
                        templateSample.scrollTo(templateMainLayout.getLeft(),0);
                    }
                });

        templateMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "You clicked row "+(childPosition+1), Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
