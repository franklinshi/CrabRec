package adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shi.crabrec.R;

import java.util.List;

import model.detail;

public class SaleAdapter extends BaseQuickAdapter<detail.Sale,BaseViewHolder> {
    private List<detail.Sale> mDatas;
    public SaleAdapter(@Nullable List<detail.Sale> data) {
        super(R.layout.template_sale,data);
        mDatas=data;
    }

    @Override
    protected void convert(BaseViewHolder holder, detail.Sale bean) {
        holder.setText(R.id.text_saleLocation, bean.getSaleLocation())
                .setText(R.id.text_saleName,  bean.getSaleName())
                .setText(R.id.text_salePhone,  bean.getSalePhone())
                .setText(R.id.text_saleTime,  bean.getSaleTime())
        ;
    }
}
