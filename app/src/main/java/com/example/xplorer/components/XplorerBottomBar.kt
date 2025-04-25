import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
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
import com.example.xplorer.ui.theme.ComponentOrange
import com.example.xplorer.ui.theme.FocusIcon
import com.example.xplorer.ui.theme.FocusedIconColor
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
    val homeTab = TabBarItem(title = XplorerScreens.Login.name, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val rankingTab = TabBarItem(title = XplorerScreens.Favorite.name, selectedIcon = Icons.Filled.Favorite, unselectedIcon = Icons.Outlined.Favorite)
    val profileTab = TabBarItem(title = XplorerScreens.Profile.name, selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

    val tabBarItems = listOf(homeTab, rankingTab, profileTab)

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

    NavigationBar (
        containerColor = NavButtomBarColor
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
                label = { Text(tabBarItem.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FocusedIconColor,
                    unselectedIconColor = ComponentOrange,
                    selectedTextColor = ComponentOrange,
                    unselectedTextColor = FocusedIconColor,
                    indicatorColor = FocusIcon
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