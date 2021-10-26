package com.keelim.free.fcm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.keelim.core.extensions.getColorCompat
import com.keelim.free.R
import com.keelim.free.notification.NotificationSpecs
import com.keelim.free.notification.NotificationSpecs.TYPE_EVENT
import com.keelim.free.notification.NotificationSpecs.TYPE_NOTICE
import com.keelim.free.ui.main.MainActivity
import timber.log.Timber

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        Timber.d("onNewToken: token=$s")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("onMessageReceived: from=${remoteMessage.from}")
        val data = remoteMessage.data
        Timber.d("Message data payload: $data")
        val type: String? = data["type"]
        val title: String? = data["title"]
        val text: String? = data["text"]
        if (type != null && title != null && text != null) {
            when (type) {
                TYPE_NOTICE -> notifyNotice(title = title, text = text)
                TYPE_EVENT -> notifyEvent(title = title, text = text)
            }
        }
    }

    private fun Context.notifyNotice(title: String, text: String) {
        NotificationSpecs.notifyAsNotice(this) {
//            setSmallIcon(R.drawable.ic_notify_default)
            setContentTitle(title)
            setContentText(text)
            setAutoCancel(true)
            setContentIntent(createLauncherIntent())
//            setColor(getColorCompat(R.color.colorSecondary))
        }
    }

    private fun Context.notifyEvent(title: String, text: String) {
        NotificationSpecs.notifyAsEvent(this) {
//            setSmallIcon(R.drawable.ic_notify_default)
            setContentTitle(title)
            setContentText(text)
            setAutoCancel(true)
            setContentIntent(createLauncherIntent())
//            setColor(getColorCompat(R.color.colorSecondary))
        }
    }

    private fun createLauncherIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }
}