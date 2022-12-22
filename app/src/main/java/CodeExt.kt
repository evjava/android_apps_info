object CodeExt {
    inline fun Boolean.doIf(callback: () -> Unit) = apply { if (this) callback() }
}