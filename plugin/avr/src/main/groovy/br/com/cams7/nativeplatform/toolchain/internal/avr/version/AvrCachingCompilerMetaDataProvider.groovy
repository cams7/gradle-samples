/**
 * 
 */
package br.com.cams7.nativeplatform.toolchain.internal.avr.version


/**
 * @author cams7
 *
 */
class AvrCachingCompilerMetaDataProvider  implements AvrCompilerMetaDataProvider {
	private final AvrCompilerMetaDataProvider delegate
	private final Map<Key, AvrVersionResult> resultMap = new HashMap<Key, AvrVersionResult>()

	private AvrCachingCompilerMetaDataProvider(AvrCompilerMetaDataProvider delegate) {
		this.delegate = delegate
	}

	@Override
	public AvrVersionResult getAvrMetaData(File avrBinary, List<String> additionalArgs) {
		Key key = new Key(avrBinary, additionalArgs)
		AvrVersionResult result = resultMap.get(key)
		if (result == null) {
			result = delegate.getAvrMetaData(avrBinary, additionalArgs)
			resultMap.put(key, result)
		}
		return result
	}


	private static class Key {
		final File avrBinary
		final List<String> args

		private Key(File avrBinary, List<String> args) {
			this.avrBinary = avrBinary
			this.args = args
		}

		@Override
		public boolean equals(Object o) {
			Key key = (Key) o
			return key.avrBinary.equals(avrBinary) && key.args.equals(args)
		}

		@Override
		public int hashCode() {
			return avrBinary.hashCode() ^ args.hashCode()
		}
	}
}
