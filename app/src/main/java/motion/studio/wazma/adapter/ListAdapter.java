//      بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ

package motion.studio.wazma.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Afriwan Ahda
 */

public class ListAdapter extends ArrayAdapter<String> {

    private int resource;
    private ArrayList<String> listSurah = new ArrayList<>();
    private LayoutInflater mInflater;

    public ListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.listSurah = objects;
        this.resource = resource;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(resource, null);
        }
        TextView item = convertView.findViewById(android.R.id.text1);
        item.setText(listSurah.get(position));
        return convertView;
    }

}
