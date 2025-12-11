package fr.upjv.projet_coop.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.upjv.projet_coop.ui.theme.NeonBlue
import fr.upjv.projet_coop.ui.theme.ProjetcoopTheme

@Composable
fun HomeScreen(
    onNavigateToFeature2: () -> Unit,
    onNavigateToFeature3: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val isLandscape = maxWidth > maxHeight

        if (isLandscape) {
            HomeLandscape(
                onNavigateToFeature2 = onNavigateToFeature2,
                onNavigateToFeature3 = onNavigateToFeature3
            )
        } else {
            HomePortrait(
                onNavigateToFeature2 = onNavigateToFeature2,
                onNavigateToFeature3 = onNavigateToFeature3
            )
        }
    }
}

@Composable
fun HomePortrait(
    onNavigateToFeature2: () -> Unit,
    onNavigateToFeature3: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        HeaderSection()

        Spacer(modifier = Modifier.height(64.dp))

        MembersSection()

        Spacer(modifier = Modifier.weight(1f))

        ActionButtonsSection(
            onNavigateToFeature2 = onNavigateToFeature2,
            onNavigateToFeature3 = onNavigateToFeature3
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun HomeLandscape(
    onNavigateToFeature2: () -> Unit,
    onNavigateToFeature3: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Side: Content (Scrollable if small height)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(32.dp))
            MembersSection()
        }

        Spacer(modifier = Modifier.width(32.dp))

        // Right Side: Buttons
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ActionButtonsSection(
                onNavigateToFeature2 = onNavigateToFeature2,
                onNavigateToFeature3 = onNavigateToFeature3
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Android Cloud 2025",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp,
                color = NeonBlue
            )
        )
        
        Text(
            text = "Master CCM",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        )
    }
}

@Composable
fun MembersSection() {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "MEMBERS",
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        MemberCard(name = "MARIN Matthieu")
        Spacer(modifier = Modifier.height(12.dp))
        MemberCard(name = "CAUWET Maxime")
    }
}

@Composable
fun ActionButtonsSection(
    onNavigateToFeature2: () -> Unit,
    onNavigateToFeature3: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ModernButton(
            text = "Feature 2 (API List)",
            onClick = onNavigateToFeature2
        )
        Spacer(modifier = Modifier.height(16.dp))
        ModernButton(
            text = "Feature 3 (Firebase)",
            onClick = onNavigateToFeature3,
            isSecondary = true
        )
    }
}

@Composable
fun MemberCard(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun ModernButton(
    text: String,
    onClick: () -> Unit,
    isSecondary: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSecondary) Color.Transparent else MaterialTheme.colorScheme.primary,
            contentColor = if (isSecondary) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
        ),
        border = if (isSecondary) ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(MaterialTheme.colorScheme.primary)
        ) else null
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Preview(widthDp = 400, heightDp = 800)
@Composable
fun HomeScreenPreviewPortrait() {
    ProjetcoopTheme {
        HomeScreen({}, {})
    }
}

@Preview(widthDp = 800, heightDp = 400)
@Composable
fun HomeScreenPreviewLandscape() {
    ProjetcoopTheme {
        HomeScreen({}, {})
    }
}
