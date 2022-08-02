package by.bsu.lsebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LseBackendApplication {
//    @Bean
//    @Throws(KeyManagementException::class, NoSuchAlgorithmException::class)
//    fun mongoDBDefaultSettings(): MongoClientSettingsBuilderCustomizer? {
//        return MongoClientSettingsBuilderCustomizer { builder: MongoClientSettings.Builder ->
//            val connectionString =
//                ConnectionString("mongodb+srv://admin:0xstFBbdROgcXlUD@mongodbcluster.52pklqz.mongodb.net/lse?retryWrites=true&w=majority")
//                builder
//                .applyConnectionString(connectionString)
//                .serverApi(
//                    ServerApi.builder()
//                        .version(ServerApiVersion.V1)
//                        .build()
//                )
//                .build()
//        }
//    }
}

fun main(args: Array<String>) {
    runApplication<LseBackendApplication>(*args)
}
