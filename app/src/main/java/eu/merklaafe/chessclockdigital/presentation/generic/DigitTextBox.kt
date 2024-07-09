package eu.merklaafe.chessclockdigital.presentation.generic

import android.text.Layout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import eu.merklaafe.chessclockdigital.R

@Composable
fun DigitTextBox(
    text: String,
    textColor: Color = Color.Black,
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                this.maxLines = maxLines
                this.setTextColor(textColor.hashCode())
                this.textSize = 200F
                this.typeface = resources.getFont(R.font.lcd_clock)
                this.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                this.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
                this.text = text
            }
        },
        modifier = modifier,
        update = {textView ->
            textView.text = text
            //textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500)
            //textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }
    )

//    AndroidView(
//        factory = {
//            View.inflate(it, R.layout.autosize_text_view,null)
//        },
//        modifier = modifier,
//        update = {
//            val textView = it.findViewById<TextView>(R.id.autosizeTextView)
//            textView.text = text
//            textView.maxLines = maxLines
//            textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM)
//        }
//    )
}

@Preview
@Composable
fun DigitTextBoxPreview(

) {
    DigitTextBox(text = "0 1 2 3 4 5 6 7 8 9")
}