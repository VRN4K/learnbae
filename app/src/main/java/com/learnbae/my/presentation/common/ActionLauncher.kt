//package ltst.nibirualert.my.presentation.common
//
//import android.content.Context
//import android.content.Intent
//import androidx.core.app.NotificationManagerCompat
//import com.github.terrakok.cicerone.Router
//import com.google.gson.Gson
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.SupervisorJob
//import ltst.nibirualert.my.R
//import ltst.nibirualert.my.data.device.AsteroidArrivalWorker
//import ltst.nibirualert.my.domain.launchIO
//import ltst.nibirualert.my.domain.datacontracts.model.ActionType
//import ltst.nibirualert.my.domain.datacontracts.model.AsteroidIntentModel
//import ltst.nibirualert.my.domain.datacontracts.model.IntentData
//import ltst.nibirualert.my.domain.interfaces.IAsteroidInteractor
//import ltst.nibirualert.my.domain.utils.DeeplinkMapper
//import ltst.nibirualert.my.presentation.screens.Screens
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//import kotlin.coroutines.CoroutineContext
//
//class ActionLauncher(private val context: Context) : KoinComponent,
//    CoroutineScope {
//    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()
//    private val router: Router by inject()
//    private val interactor: IAsteroidInteractor by inject()
//
//    private fun fromJson(value: String) = Gson().fromJson(value, IntentData::class.java)
//
//    private fun navigateToAsteroidPage(asteroidIntentModel: AsteroidIntentModel) {
//        router.navigateTo(
//            Screens.getAsteroidPageFragment(
//                asteroidIntentModel.asteroidId,
//                asteroidIntentModel.asteroidName
//            )
//        )
//    }
//
//    private fun shareAsteroid(asteroidIntentModel: AsteroidIntentModel) {
//        context.startActivity(
//            Intent.createChooser(
//                Intent(Intent.ACTION_SEND).apply {
//                    type = "text/plain"
//                    putExtra(
//                        Intent.EXTRA_TEXT,
//                        String.format(
//                            context.resources.getString(R.string.share_notification_message),
//                            asteroidIntentModel.asteroidArrivalTime,
//                            asteroidIntentModel.asteroidName,
//                            DeeplinkMapper().createLink(
//                                Pair("https", "nibirualert.ltst")
//                            )
//                        )
//                    )
//                },
//                context.resources.getString(R.string.share_title)
//            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        )
//        NotificationManagerCompat.from(context).cancel(AsteroidArrivalWorker.NOTIFICATION_ID)
//    }
//
//    private fun removeAsteroidFromFavorites(asteroidIntentModel: AsteroidIntentModel) {
//        launchIO { interactor.removeAsteroidFromFavourites(asteroidIntentModel.asteroidId)
//            NotificationManagerCompat.from(context).cancel(AsteroidArrivalWorker.NOTIFICATION_ID)}
//    }
//
//    fun action(intent: Intent) {
//        val intentData = fromJson(intent.getStringExtra(AsteroidArrivalWorker.DATA_KEY)!!)
//
//        when (intentData.actionType) {
//            ActionType.NAVIGATE -> navigateToAsteroidPage(intentData.asteroidIntentModel)
//            ActionType.REMOVE -> removeAsteroidFromFavorites(intentData.asteroidIntentModel)
//            ActionType.SHARE -> shareAsteroid(intentData.asteroidIntentModel)
//        }
//    }
//}