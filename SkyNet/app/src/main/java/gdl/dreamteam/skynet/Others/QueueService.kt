/*package gdl.dreamteam.skynet.Others

import android.util.Log
import com.microsoft.azure.servicebus.*
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder
import com.microsoft.azure.servicebus.primitives.ServiceBusException
/**
 * Created by Don miguelon on 20/09/2017.
 */

object QueueService {

    lateinit var messageSender : IMessageSender

    fun configure(connectionString : String, queueName : String){
        messageSender = ClientFactory
                .createMessageSenderFromConnectionStringBuilder(
                        ConnectionStringBuilder(connectionString, queueName))
    }

    fun sendMessage(message : String){
        messageSender.sendAsync(Message(message)).thenRunAsync {
            Log.d("MESSAGE_QUEUE", "Message sent")
        }
    }
}*/