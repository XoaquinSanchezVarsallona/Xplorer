import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.UnselectedColor
import com.example.xplorer.ui.theme.FocusIcon
import com.example.xplorer.ui.theme.FocusedIconColor
import com.example.xplorer.ui.theme.Greyscale500
import com.example.xplorer.ui.theme.NavButtomBarColor
import com.example.xplorer.ui.theme.XplorerTheme


@Composable
fun XplorerBottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(
        onNavigate = { screen -> navController.navigate(screen) }
    )
}

@Preview(showBackground = true)
@Composable
fun XplorerBottomBarPreviewLight() {
    XplorerTheme {
        XplorerBottomBarPreview()
    }
}

@Composable
fun BottomBar(
    onNavigate: (String) -> Unit,
) {
    val homeTab = TabBarItem(title = XplorerScreens.Home.name, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Filled.Home)
    val rankingTab = TabBarItem(title = XplorerScreens.Favorite.name, selectedIcon = Icons.Filled.Favorite, unselectedIcon = Icons.Filled.Favorite)

    val tabBarItems = listOf(homeTab, rankingTab)

    TabView(tabBarItems, onNavigate)
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

@Composable
fun TabView(tabBarItems: List<TabBarItem>, onNavigate: (String) -> Unit) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onNavigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
                label = { Text(tabBarItem.title, color = MaterialTheme.colorScheme.onSurface) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.tertiary
                )
            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}