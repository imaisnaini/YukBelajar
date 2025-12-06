package com.unsia.yukbelajar.data;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.unsia.yukbelajar.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    Context context;
    List<ItemModel> list;
    TextToSpeech tts;
    TtsReadyListener ttsReadyListener;

    public interface TtsReadyListener {
        boolean isReady();
    }

    public ItemAdapter(Context context, List<ItemModel> list, TextToSpeech tts, TtsReadyListener listener){
        this.context = context;
        this.list = list;
        this.tts = tts;
        this.ttsReadyListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel Item = list.get(position);
        holder.txtTitle.setText(Item.name);
        holder.imgIcon.setImageResource(Item.image);// Reset icon warna default


        holder.cardView.setOnClickListener(v -> {
            if (ttsReadyListener.isReady()) {
                tts.speak(Item.name, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                Toast.makeText(context, "Tunggu sebentar, TTS belum siap...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        TextView txtTitle;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            cardView = (CardView) itemView;
        }
    }
}
