package com.coderio.themoviedb.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coderio.themoviedb.R
import com.coderio.themoviedb.ui.TrendytAppFoundation
import com.coderio.themoviedb.ui.theme.TrendytTheme

@Composable
fun RetryScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.no_network_message),
    messageStyle: TextStyle = LocalTextStyle.current,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(70.dp),
            painter = painterResource(id = R.drawable.exclamation),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            style = messageStyle,
            text = message
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(id = R.string.retry_action))
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewRetryScreen() {
    TrendytAppFoundation {
        Surface {
            RetryScreen(
                modifier = Modifier.fillMaxSize()
            ) {
            }
        }
    }
}
