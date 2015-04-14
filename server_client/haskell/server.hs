{-# LANGUAGE OverloadedStrings #-}
import Network.Wai
import Network.Wai.Handler.Warp
import Blaze.ByteString.Builder (fromByteString)
import Network.HTTP.Types (status200)

main = run 8000 $ const $ return $ responseBuilder
    status200
    [("Content-Type", "text/plain"), ("Content-Length", "4")]
    $ fromByteString "Test"
