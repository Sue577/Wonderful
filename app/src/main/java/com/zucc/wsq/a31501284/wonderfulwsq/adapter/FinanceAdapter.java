package com.zucc.wsq.a31501284.wonderfulwsq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zucc.wsq.a31501284.wonderfulwsq.R;
import com.zucc.wsq.a31501284.wonderfulwsq.litepalData.FinanceRecord;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by dell on 2018/7/14.
 */

public class FinanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<FinanceRecord> list;
    Context context;
    String pack;

    private class FinanceViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView number;
        TextView date;
        RelativeLayout content;
        Button delete;

        public FinanceViewHolder(View itemView) {
            super(itemView);
            img= (ImageView)itemView.findViewById(R.id.img);
            title= (TextView) itemView.findViewById(R.id.title);
            number= (TextView) itemView.findViewById(R.id.number);
            date= (TextView) itemView.findViewById(R.id.date);
            content= (RelativeLayout) itemView.findViewById(R.id.content);
            delete = (Button)itemView.findViewById(R.id.delete);

        }
    }

    public FinanceAdapter(Context context, ArrayList<FinanceRecord> list, String pack) {
        this.list = list;
        this.context=context;
        this.pack=pack;

    }
    @Override
    public FinanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finance_record, parent, false);
        final FinanceViewHolder holder = new FinanceViewHolder(view);
        //删除记录
        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                FinanceRecord Detail = list.get(position);
                Toast.makeText(v.getContext(),"删除记录成功~",Toast.LENGTH_SHORT).show();

                //数据库删除操作
                DataSupport.deleteAll(FinanceRecord.class,"financeTime=?",Detail.getFinanceTime());

                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FinanceViewHolder) {

            ((FinanceViewHolder) holder).content.setVisibility(View.VISIBLE);
            String imgpath=list.get(position).getFinanceTypeImage();
            //通过文件名获取图片
            ((FinanceViewHolder) holder).img.setImageResource(((FinanceViewHolder) holder).content.getResources().getIdentifier("ic_finance_type_"+imgpath,"mipmap",pack));

            //显示类型名称和金额
            ((FinanceViewHolder) holder).title.setText(list.get(position).getFinanceTypeName());
            ((FinanceViewHolder) holder).number.setText(list.get(position).getFinancePrice()+"");
            if (list.get(position).getFinancePrice()>0) {
                ((FinanceViewHolder) holder).number.setTextColor(((FinanceViewHolder) holder).content.getResources().getColor(R.color.income));
            }else {
                ((FinanceViewHolder) holder).number.setTextColor(((FinanceViewHolder) holder).content.getResources().getColor(R.color.pay));
            }

            //显示时间
            ((FinanceViewHolder) holder).date.setText(list.get(position).getFinanceTime());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
