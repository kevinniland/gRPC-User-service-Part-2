package ie.gmit.ds;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class UserClient {
	private static final Logger logger = Logger.getLogger(UserClient.class.getName());
	private final ManagedChannel channel;
	private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
	private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

	HashRequest hashRequest;
	HashResponse hashResponse;

	byte[] saltArray;
	ByteString salt;
	ByteString passwordHashed;

	// Gets user salt
	public ByteString getUserSalt() {
		return salt;
	}

	// Gets user hashed password
	public ByteString getUserPasswordHashed() {
		return passwordHashed;
	}

	public UserClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();

		syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
		asyncPasswordService = PasswordServiceGrpc.newStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void hashRequest(int userId, String userPassword) throws InterruptedException {
		StreamObserver<HashResponse> streamObserver = new StreamObserver<HashResponse>() {
			@Override
			public void onNext(HashResponse hashResponse) {
				passwordHashed = hashResponse.getHashedPassword();
				salt = hashResponse.getSalt();
			}

			@Override
			public void onError(Throwable throwable) {
				logger.info(throwable.getLocalizedMessage());
			}

			@Override
			public void onCompleted() {

			}
		};

		try {
			HashRequest hashRequest = HashRequest.newBuilder().setUserID(userId).setPassword(userPassword).build();

			asyncPasswordService.hash(hashRequest, streamObserver);

			TimeUnit.SECONDS.sleep(3);
		} catch (RuntimeException runtimeException) {
			logger.info(runtimeException.getLocalizedMessage());
		}
	}

	public boolean passwordValidation(String userPassword, ByteString userHashedPassword, ByteString userSalt) {
		ValidateRequest validateRequest = ValidateRequest.newBuilder().setPassword(userPassword)
				.setHashedPassword(passwordHashed).setSalt(salt).build();

		try {
			BoolValue boolValue = syncPasswordService.validate(validateRequest);

			return boolValue.getValue();
		} catch (RuntimeException runtimeException) {
			logger.info(runtimeException.getLocalizedMessage());

			return false;
		}
	}

	public static void main(String[] args) {
		ServerGrpc serverGrpc = new ServerGrpc();
		serverGrpc.serverMesage("Hello");
	}
}
