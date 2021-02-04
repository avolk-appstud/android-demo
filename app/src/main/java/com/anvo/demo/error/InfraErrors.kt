package com.anvo.demo.error

import java.io.IOException

/**
 * This error indicates that no network connection is available (no wifi, no mobile network)
 */
class NoConnectivityException(message: String) : IOException(message)