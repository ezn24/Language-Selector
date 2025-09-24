package vegabobo.languageselector.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vegabobo.languageselector.R
import vegabobo.languageselector.ui.screen.main.AppInfo
import vegabobo.languageselector.ui.screen.main.AppLabels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    query: String,
    onUpdatedValue: (String) -> Unit,
    onSearch: (String) -> Unit,
    apps: List<AppInfo> = emptyList(),
    history: List<AppInfo> = emptyList(),
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedLabels: List<AppLabels>,
    onSelectedLabelsChange: (AppLabels) -> Unit,
    onClickApp: (AppInfo) -> Unit,
    onClickClear: () -> Unit,
    actions: @Composable RowScope.() -> Unit,
) {
    SearchBar(
        modifier = Modifier
            .semantics { isTraversalGroup = true }
            .then(modifier),
        inputField = {
            SearchBarDefaults.InputField(
                onSearch = { onSearch(it) },
                expanded = isExpanded,
                onExpandedChange = { onExpandedChange(it) },
                placeholder = { Text(placeholder) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Row { actions() }
                },
                query = query,
                onQueryChange = { onUpdatedValue(it) }
            )
        },
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange(it) },
    ) {
        LazyColumn {
            if (query.isNotBlank()) {
                item {
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 23.dp,
                                top = 8.dp,
                                bottom = 8.dp,
                                end = 8.dp
                            )
                            .horizontalScroll(rememberScrollState())
                    ) {
                        FilterLabel(
                            title = "Show System",
                            onClick = {
                                onSelectedLabelsChange(AppLabels.SYSTEM_APP)
                            },
                            isSelected = selectedLabels.contains(AppLabels.SYSTEM_APP)
                        )
                        Spacer(Modifier.padding(8.dp))
                        FilterLabel(
                            title = "Show Modified",
                            onClick = { onSelectedLabelsChange(AppLabels.MODIFIED) },
                            isSelected = selectedLabels.contains(AppLabels.MODIFIED)
                        )
                    }
                }

                if (apps.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 23.dp, vertical = 16.dp)
                                .alpha(0.6f),
                            text = stringResource(id = R.string.search_no_results),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(apps.size) {
                        val app = apps[it]
                        AppListItem(
                            modifier = Modifier.padding(
                                start = 23.dp,
                                end = 23.dp,
                                top = 4.dp,
                                bottom = 4.dp
                            ),
                            app = app,
                            onClickApp = { onClickApp(app) }
                        )
                    }
                }
            } else if (history.isNotEmpty()) {
                item {
                    Row(
                        Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "History".uppercase(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = modifier
                                .padding(start = 18.dp)
                                .padding(bottom = 8.dp)
                                .padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { onClickClear() }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Clear")
                            }
                        }
                        Spacer(modifier = Modifier.padding(6.dp))
                    }
                }
                items(history.size) {
                    val app = history[it]
                    AppListItem(
                        modifier = Modifier.padding(
                            start = 23.dp,
                            end = 23.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        ),
                        app = app,
                        onClickApp = { onClickApp(app) }
                    )
                }
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(Modifier.weight(1f))

                    }
                }
            } else {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .alpha(0.4f),
                        text = stringResource(id = R.string.search_start_typing),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    if (query.isNotBlank())
        BackHandler {
            onUpdatedValue("")
        }
}

