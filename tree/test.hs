data Tree a = Empty | Node a (Tree a) (Tree a)

expand:: Int -> Int -> Tree Int
expand _ 0 = Empty
expand init level = Node init (expand init (level - 1)) (expand init (level - 1))

sumit:: Tree Int -> Int  
sumit Empty = 0
sumit (Node value left right) = value + sumit left + sumit right

printexpand:: Int -> IO()
printexpand i = putStrLn(show i ++ ": " ++ show (sumit (expand (25 - i) i)))

main:: IO()
main = do
    mapM_ (mapM_ printexpand) (replicate 100 [1,2..20])
