
package gdl.dreamteam.skynet.Others

import android.util.Log
import com.microsoft.azure.storage.CloudStorageAccount
import com.microsoft.azure.storage.queue.CloudQueue
import com.microsoft.azure.storage.queue.CloudQueueMessage

/**
 * Created by Don miguelon on 20/09/2017.
 */

object QueueService {

    lateinit var queue : CloudQueue

    fun configure(connectionString : String, queueName : String){
        val account = CloudStorageAccount.parse(connectionString)
        val cloudClient = account.createCloudQueueClient()
        queue = cloudClient.getQueueReference(queueName)
    }

    fun sendMessage(message : String){
        queue.addMessage(CloudQueueMessage(message))
    }
}