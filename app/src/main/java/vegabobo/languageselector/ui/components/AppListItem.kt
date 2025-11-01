package vegabobo.languageselector.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import vegabobo.languageselector.R
import vegabobo.languageselector.ui.screen.main.AppInfo

private val SystemLabelBackground = Color(0xFFE0E0E0)
private val SystemLabelContent = Color(0xFF424242)
private val UserLabelBackground = Color(0xFFBBDEFB)
private val UserLabelContent = Color(0xFF0D47A1)
private val ModifiedLabelBackground = Color(0xFFFFE0B2)
private val ModifiedLabelContent = Color(0xFFE65100)

@Composable
fun AppListItem(
    modifier: Modifier = Modifier,
    app: AppInfo,
    onClickApp: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClickApp(app.pkg) }
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            bitmap = app.icon.toBitmap().asImageBitmap(),
            contentDescription = "app icon"
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ) {
            Text(text = app.name, fontSize = 18.sp, fontWeight = FontWeight.Medium, maxLines = 1)
            Text(text = app.pkg, fontSize = 12.sp, maxLines = 1)
            Row {
                val (appTypeLabel, appTypeBackground, appTypeContent) = if (app.isSystemApp()) {
                    Triple(
                        stringResource(id = R.string.system_app_label),
                        SystemLabelBackground,
                        SystemLabelContent
                    )
                } else {
                    Triple(
                        stringResource(id = R.string.user_app_label),
                        UserLabelBackground,
                        UserLabelContent
                    )
                }
                TextLabel(
                    text = appTypeLabel,
                    backgroundColor = appTypeBackground,
                    contentColor = appTypeContent
                )
                if (app.isModified()) {
                    TextLabel(
                        text = stringResource(id = R.string.label_modified),
                        backgroundColor = ModifiedLabelBackground,
                        contentColor = ModifiedLabelContent
                    )
                }
            }
        }
    }
}

@Composable
fun TextLabel(
    text: String,
    backgroundColor: Color,
    contentColor: Color
) {
    Box(Modifier.padding(top = 2.dp, end = 4.dp, bottom = 4.dp)) {
        Box(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
                text = text,
                maxLines = 1,
                lineHeight = 16.sp,
                fontSize = 10.sp,
                color = contentColor
            )
        }
    }
}